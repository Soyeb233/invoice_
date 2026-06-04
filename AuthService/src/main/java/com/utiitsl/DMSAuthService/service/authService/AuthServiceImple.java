package com.utiitsl.DMSAuthService.service.authService;

import com.utiitsl.DMSAuthService.dto.AuthenticationResponseDTO;
import com.utiitsl.DMSAuthService.dto.LoginRequestDTO;
import com.utiitsl.DMSAuthService.entity.RefreshToken;
import com.utiitsl.DMSAuthService.entity.User;
import com.utiitsl.DMSAuthService.repository.UserRepository;
import com.utiitsl.DMSAuthService.service.jwtService.JwtService;
import com.utiitsl.DMSAuthService.service.refreshTokenService.RefreshTokenService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AuthServiceImple implements AuthService {

    @Value("${application.security.jwt.access-token-expiration}")
    private long ACCESS_TOKEN_EXPIRE;

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final RefreshTokenService refreshTokenService;

//    @Override
//    public AuthenticationResponseDTO register(RegisterRequestDTO registerRequest) {
//
//            Optional<User> userOptional = userRepository.findByUsername(registerRequest.getUsername());
//            Optional<User> userEmailOptional=userRepository.findByEmail(registerRequest.getEmail());
//            if (userOptional.isPresent()) {
//                throw new UserDefinedException("USER ALREADY EXISTS", HttpStatus.CONFLICT);
//            }
//            if(userEmailOptional.isPresent()){
//                throw new UserDefinedException(ErrorMessage.EMAIL_ALREADY_EXISTS,HttpStatus.CONFLICT);
//            }
//            try{
//            User user = User.builder()
//                    .username(registerRequest.getUsername())
//                    .password(passwordEncoder.encode(registerRequest.getPassword()))
//                    .originalPassword(registerRequest.getPassword())
//                    .firstName(registerRequest.getFirstName())
//                    .lastName(registerRequest.getLastName())
//                    .email(registerRequest.getEmail())
//                    .role(registerRequest.getRole()).build();
//
//            User savedUser = userRepository.save(user);
//            System.out.println("AFTER UPDATING RECORDS :" + savedUser.getId());
//            String jwtToken = jwtService.generateAccessToken(user);
//            return AuthenticationResponseDTO.builder()
//                    .token(jwtToken)
//                    .role(savedUser.getRole().toString())
//                    .status(HttpStatus.OK).build();
//        }
//            catch(Exception ex){
//                ex.printStackTrace();
//                throw new UserDefinedException("SOMETHING GOES WRONGS",HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//
//    }

    @Override
    public AuthenticationResponseDTO login(LoginRequestDTO loginRequest) {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            User user = userRepository.findByUsernameOrEmail(loginRequest.getUsername(),loginRequest.getUsername()).orElseThrow();

            String token = jwtService.generateAccessToken(user);

        RefreshToken refreshToken=refreshTokenService.createRefreshToken(user.getUsername());

        System.out.println("PRINTNTING THE ROLE :"+user.getRole().toString());
            return AuthenticationResponseDTO.builder()
                    .token(token)
                    .refreshToken(refreshToken.getRefreshToken())
                    .status(HttpStatus.OK)
                    .role(user.getRole().toString())
                    .username(user.getUsername())
                    .isAuthenticated(true)
                    .tokenExpire(ACCESS_TOKEN_EXPIRE)
                    .build();
    }

    @Override
    public AuthenticationResponseDTO login(
            LoginRequestDTO loginRequest,
            HttpServletResponse response
    ) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        User user = userRepository
                .findByUsernameOrEmail(
                        loginRequest.getUsername(),
                        loginRequest.getUsername()
                )
                .orElseThrow();

        String accessToken = jwtService.generateAccessToken(user);

        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(
                        user.getUsername()
                );

        // ACCESS TOKEN COOKIE
        ResponseCookie accessCookie =
                ResponseCookie.from("accessToken", accessToken)
                        .httpOnly(true)
                        .secure(false) // localhost only
                        .path("/")
                        .sameSite("Lax")
                        .maxAge(Duration.ofDays(1))
                        .build();

        // REFRESH TOKEN COOKIE
        ResponseCookie refreshCookie =
                ResponseCookie.from(
                                "refreshToken",
                                refreshToken.getRefreshToken()
                        )
                        .httpOnly(true)
                        .secure(false)
                        .path("/")
                        .maxAge(Duration.ofDays(7))
                        .sameSite("Lax")
                        .build();

        response.addHeader(
                HttpHeaders.SET_COOKIE,
                accessCookie.toString()
        );



        return AuthenticationResponseDTO.builder()
                .status(HttpStatus.OK)
                .role(user.getRole().toString())
                .username(user.getUsername())
                .isAuthenticated(true)
                .tokenExpire(ACCESS_TOKEN_EXPIRE)
                .build();
    }

}
