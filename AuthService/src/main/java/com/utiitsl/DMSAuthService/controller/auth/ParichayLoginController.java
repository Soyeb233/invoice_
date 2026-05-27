package com.utiitsl.DMSAuthService.controller.auth;

import com.utiitsl.DMSAuthService.common.response.APIResponse;
import com.utiitsl.DMSAuthService.common.response.ResponseHandler;
import com.utiitsl.DMSAuthService.constants.Role;
import com.utiitsl.DMSAuthService.dto.AuthenticationResponseDTO;
import com.utiitsl.DMSAuthService.entity.RefreshToken;
import com.utiitsl.DMSAuthService.entity.User;
import com.utiitsl.DMSAuthService.repository.UserRepository;
import com.utiitsl.DMSAuthService.service.jwtService.JwtService;
import com.utiitsl.DMSAuthService.service.parichayClientService.ParichayClientService;
import com.utiitsl.DMSAuthService.service.parichayClientService.ParichayClientServiceImpl;
import com.utiitsl.DMSAuthService.service.refreshTokenService.RefreshTokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class ParichayLoginController {

    private static Logger logger = LoggerFactory.getLogger(ParichayLoginController.class);

    private final ParichayClientService parichayClientService;

    private String acessToken = null;

    @PostMapping("/redirectUrlToParichay")
    public ResponseEntity<APIResponse> loginWithParichay(HttpSession httpSession, HttpServletRequest request)throws Exception, Exception{
        System.err.println("REQUEST TO REDIRECT ON ANOTHER URL");
        return ResponseHandler.generateResponse(parichayClientService.generateCodeVerifier(httpSession,request), HttpStatus.OK,true);
    }

    @GetMapping("/redirect-url-dms")
    public void usrauthGetTokenTest(@RequestParam(value = "code", required = false) String authorizationCode,
                                    @RequestParam("state") String state,
                                    HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        logger.info("authorizationCode at controller: " + authorizationCode);

        // Generate the redirect URL from your service
        String redirectUrl = parichayClientService.redirectUrlToUI(authorizationCode, state);
        logger.info("REDIRECT LINK TO UI {}",redirectUrl);
        // Send a redirect response to the client with the URL
        response.sendRedirect(redirectUrl);
    }

    @PostMapping("/usrauthFromParichay")
    public ResponseEntity<APIResponse> usrauthFromParichay(@RequestParam(value = "code",required = false) String authorizationCode, @RequestParam("state") String state,
                                                       HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        logger.info("authorizationCode  at controller {} ", authorizationCode);
        logger.info("state  at controller for use of CodeVerifier further {} ",state);

        return ResponseHandler.generateResponse(parichayClientService.userAuthenticationFromParichay(authorizationCode,state),HttpStatus.OK,true);
    }


    @GetMapping("/logOutParichay")
    public ResponseEntity<APIResponse> logOutParichay(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
//        request.getSession().setAttribute("acess_Token", acessToken);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        logger.info(" Here is the authentication object : " + authentication.toString());
//        parichayClientService.onLogoutSuccess(request, response, authentication);
        logger.info("User Logout Successfully : " + authentication.toString());
        return ResponseHandler.generateResponse("Logout Successfully",HttpStatus.OK,true);
    }
}
