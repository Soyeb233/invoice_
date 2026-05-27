package com.utiitsl.DMSAuthService.controller.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.utiitsl.DMSAuthService.common.response.APIResponse;
import com.utiitsl.DMSAuthService.common.response.ResponseHandler;
import com.utiitsl.DMSAuthService.constants.LoggerMessage;
import com.utiitsl.DMSAuthService.dto.AuthenticationResponseDTO;
import com.utiitsl.DMSAuthService.dto.LoginRequestDTO;
import com.utiitsl.DMSAuthService.dto.RefreshTokenRequest;
import com.utiitsl.DMSAuthService.dto.RegisterRequestDTO;
import com.utiitsl.DMSAuthService.service.authService.AuthService;
import com.utiitsl.DMSAuthService.service.jwtService.JwtService;
import com.utiitsl.DMSAuthService.service.logger.LogService;
import com.utiitsl.DMSAuthService.service.refreshTokenService.RefreshTokenService;
import com.utiitsl.DMSAuthService.service.userService.UserService;
import com.utiitsl.DMSAuthService.util.LoggerUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

import static com.utiitsl.DMSAuthService.constants.LoggerMessage.MODULE_NAME;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@CrossOrigin("*")
@Slf4j
public class AuthController {

//    @Autowired
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final LogService loggerService;
    private final UserService userService;


    @PostMapping("/token")
    public ResponseEntity<APIResponse> login(@RequestBody LoginRequestDTO loginRequest, @RequestParam(value = "geoLocation", required = false) String geoLocation,
                                             HttpServletRequest request){

        return ResponseHandler.generateResponse(authService.login(loginRequest),HttpStatus.OK,true);
    }

    @PostMapping("/refresh")
    public ResponseEntity<APIResponse> refreshJwtToken(@RequestBody RefreshTokenRequest request){
        AuthenticationResponseDTO loginRequestDTO= refreshTokenService.generateRefreshToken(request);
        System.err.println("AFTER TOKEN REFRESH :");
        System.out.println(loginRequestDTO);
       return ResponseHandler.generateResponse(refreshTokenService.generateRefreshToken(request),HttpStatus.OK,true);

    }

    @PostMapping("/tokenWithCookie")
    public ResponseEntity<APIResponse> loginTokenUsingCookie(@RequestBody LoginRequestDTO loginRequest, @RequestParam(value = "geoLocation", required = false) String geoLocation,
                                                             HttpServletResponse response){

        return ResponseHandler.generateResponse(authService.login(loginRequest,response),HttpStatus.OK,true);
    }

    @PostMapping("/create")
    public ResponseEntity<APIResponse> register(@RequestBody RegisterRequestDTO registerRequest, @RequestParam(value = "geoLocation", required = false) String geoLocation,
                                                HttpServletRequest request){


        return ResponseHandler.generateResponse(userService.createUser(registerRequest),HttpStatus.OK,true);
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(
            Authentication authentication
    ) {

        if (authentication == null) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(
                Map.of(
                        "username", authentication.getName(),
                        "roles", authentication.getAuthorities()
                )
        );
    }
}
