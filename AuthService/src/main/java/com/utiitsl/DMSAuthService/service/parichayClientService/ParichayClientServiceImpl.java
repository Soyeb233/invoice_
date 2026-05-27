package com.utiitsl.DMSAuthService.service.parichayClientService;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.utiitsl.DMSAuthService.common.exceptionHandler.UserDefinedException;
import com.utiitsl.DMSAuthService.common.objectMapperBinde.ObjectMapperUtil;
import com.utiitsl.DMSAuthService.common.response.ResponseHandler;
import com.utiitsl.DMSAuthService.dto.AuthenticationResponseDTO;
import com.utiitsl.DMSAuthService.dto.parichayClientDTO.ParichayClientRequestDTO;
import com.utiitsl.DMSAuthService.dto.parichayClientDTO.ParichayTokenDTO;
import com.utiitsl.DMSAuthService.dto.parichayClientDTO.ParichayUserDetailsDTO;
import com.utiitsl.DMSAuthService.entity.RefreshToken;
import com.utiitsl.DMSAuthService.entity.User;
import com.utiitsl.DMSAuthService.repository.UserRepository;
import com.utiitsl.DMSAuthService.service.jwtService.JwtService;
import com.utiitsl.DMSAuthService.service.refreshTokenService.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ParichayClientServiceImpl implements ParichayClientService {

    private static Logger logger = LoggerFactory.getLogger(ParichayClientServiceImpl.class);

    @Value("${parichay.serviceId}")
    private String serviceId;

    @Value("${parichay.clientSecret}")
    private String clientSecret;

    @Value("${parichay.client-redirect-uri}")
    private String clientRedirectUri;

    @Value("${parichay.client-authorization-grant-type}")
    private String clientGrantType;

    @Value("${parichay.client-scope}")
    private String clientScope;

    @Value("${parichay.client-codeChallengeMethod}")
    private String clientCodeChallengeMethod;

    @Value("${parichay.client-State}")
    private String clientState;

    @Value("${parichay.client-issuer-url}")
    private String clientIssuerUrl;

    @Value("${parichay.request-token-api}")
    private String parichayRequestTokenAPI;

    @Value("${parichay.request-userDetails}")
    private String parichayRequestUserDetails;


    @Value("${dms.redirect-url.ui}")
    private String dmsRedirectUrlUI;

    @Value("${dms.redirect-url.error}")
    private String dmsRedirectUrlError;
    @Value("${application.security.jwt.access-token-expiration}")
    private long ACCESS_TOKEN_EXPIRE;

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final ObjectMapper objectMapper;



    @Bean
    public static RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        ((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setConnectTimeout(250000);
        ((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setReadTimeout(250000);
        return restTemplate;
    }

    /* Here Starting the Authorization Code generation process */
    @Override
    public String generateCodeVerifier(HttpSession session,HttpServletRequest request) throws UnsupportedEncodingException, Exception {
        SecureRandom secureRandom = new SecureRandom();
        byte[] codeVerifier = new byte[32];
        secureRandom.nextBytes(codeVerifier);
        String normalcodeVerifier = Base64.getUrlEncoder().withoutPadding().encodeToString(codeVerifier);
        logger.info(" Codeverifier:  " + normalcodeVerifier);
        String codeChallenge = generateCodeChallange(
                Base64.getUrlEncoder().withoutPadding().encodeToString(codeVerifier));
        logger.info("Encrypted  codeverifier : " + codeChallenge);

        String redirectUrl = String.format(
                "%s" + "?response_type=code" + "&serviceId=%s" + "&redirect_uri=%s" + "&scope=%s" + "&code_challenge=%s"
                        + "&code_challenge_method=%s" + "&state=%s",
                clientIssuerUrl, serviceId, clientRedirectUri, clientScope, codeChallenge, clientCodeChallengeMethod,
                normalcodeVerifier);

        System.err.println("REDIRECT URL :"+ redirectUrl);
        logger.info("redirectUrl : " + redirectUrl);
        request.getSession().setAttribute("code_verifier", normalcodeVerifier);

        return redirectUrl;

    }

    @Override
    public String generateCodeChallange(String codeVerifier)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {

        byte[] bytes = codeVerifier.getBytes("US-ASCII");
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(bytes, 0, bytes.length);
        byte[] digest = messageDigest.digest();
        return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
    }

    @Override
    public String redirectUrlToUI(String authorizationCode, String state) {

        // ERROR
        // http://localhost:3000/error?message=Token%20exchange%20failed
        if(authorizationCode!=null){
            String redirectWithParams=dmsRedirectUrlUI + "?code=" + authorizationCode + "&state=" + state;
            System.err.println(redirectWithParams);
            return redirectWithParams;
        }
        System.err.println(dmsRedirectUrlError);
        return dmsRedirectUrlError;
    }

    @Override
    public AuthenticationResponseDTO userAuthenticationFromParichay(String authorizationCode, String state) {

        ParichayTokenDTO parichayTokenDTO = getAccessToken(authorizationCode,state);
        if (parichayTokenDTO == null) {
            throw new UserDefinedException("Bad Credential for token request please try again..!",HttpStatus.NOT_FOUND);
        }
        ParichayUserDetailsDTO parichayUserDetailsDTO=getUserDetailsResponse(parichayTokenDTO.getAccessToken());
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

//        boolean isActivated=Boolean.parseBoolean(parichayUserDetailsDTO.getStatus());
        User user = userRepository.findByEmail(parichayUserDetailsDTO.getEmailId())
                .orElseThrow(() -> new UserDefinedException("User with email " + parichayUserDetailsDTO.getEmailId() + " not found",HttpStatus.NOT_FOUND));

        if (!user.isActiveStatus()) {
            throw new DisabledException("User is inactive");
        }
        String token = jwtService.generateAccessToken(user);

        RefreshToken refreshToken=refreshTokenService.createRefreshToken(user.getUsername());

        System.out.println("PRINTING THE ROLE :"+user.getRole().toString());
        AuthenticationResponseDTO authenticationResponseDTO= AuthenticationResponseDTO.builder()
                .token(token)
                .refreshToken(refreshToken.getRefreshToken())
                .status(HttpStatus.OK)
                .role(user.getRole().toString())
                .username(user.getUsername())
                .isAuthenticated(true)
                .tokenExpire(ACCESS_TOKEN_EXPIRE)
                .build();
        logger.info("USER LOGIN SUCCESSFULLY {}",authenticationResponseDTO);
        return authenticationResponseDTO;
    }

    @Override
    public boolean isAccessTokenValid(Long expiredIn, Date createdAt) {

        return false;
    }

    private ParichayTokenDTO getAccessToken(String authorizationCode, String codeVerifier) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            logger.info("Code Verifier at getAccessToken: " + codeVerifier);

            ParichayClientRequestDTO parichayClientRequestDT=ParichayClientRequestDTO.builder()
                    .client_id(serviceId)
                    .client_secret(clientSecret)
                    .redirect_uri(clientRedirectUri)
                    .grant_type(clientGrantType)
                    .code(authorizationCode)
                    .code_verifier(codeVerifier)
                    .build();

            String jsonRequest = objectMapper.writeValueAsString(parichayClientRequestDT);
            System.err.println(jsonRequest);
            logger.info("JSON REQUEST FOR TOKEN {}",jsonRequest);

            // CALL TO REUQEST TOKEN
            HttpEntity<String> entity = new HttpEntity<>(jsonRequest, headers);
            ResponseEntity<String> responseEntity = getRestTemplate()
                    .exchange(parichayRequestTokenAPI, HttpMethod.POST, entity, String.class);

            // RETIVE THE TOKEN IN STRING
            String tokenResponse = responseEntity.getBody();
            logger.info("Token response: " + tokenResponse);

            ParichayTokenDTO parichayTokenDTO=null;
            try {
                parichayTokenDTO = objectMapper.readValue(tokenResponse, ParichayTokenDTO.class);
                logger.info("Successfully parsed ParichayTokenDTO: " + parichayTokenDTO);
            } catch (Exception e) {

                logger.error("Error parsing token response to DTO", e);
                throw new UserDefinedException("Error parsing token response to DTO",HttpStatus.BAD_REQUEST);
            }
            return  parichayTokenDTO;
        }
        catch (UserDefinedException ex){
            throw new UserDefinedException(ex.getMessage(),ex.getStatus());
        }
        catch (Exception ex) {
            logger.info("TOKEN API FAILD {}",ex.getMessage());
            throw new UserDefinedException(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    private ParichayUserDetailsDTO getUserDetailsResponse(String accessToken) {
        try {
            HttpHeaders userApiHeaders = new HttpHeaders();
            userApiHeaders.set("Authorization", accessToken);
            userApiHeaders.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> userApiEntity = new HttpEntity<>(userApiHeaders);

            ResponseEntity<String> responseEntity = getRestTemplate().exchange(
                    parichayRequestUserDetails, HttpMethod.GET, userApiEntity,
                    String.class);
            logger.info(responseEntity.getBody());
            ParichayUserDetailsDTO parichayUserDetailsDTO= objectMapper.
                    readValue(responseEntity.getBody(), ParichayUserDetailsDTO.class);
            logger.info("PRINTING USERDETAILS OBJECT RETRIVE FROM PARICHAY {}",parichayUserDetailsDTO);
            return parichayUserDetailsDTO;
        } catch (Exception e) {
            logger.error("Error while fetching user details:", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


}
