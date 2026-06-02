package com.utiitsl.DMSAuthService.config.auth;

import com.utiitsl.DMSAuthService.entity.User;
import com.utiitsl.DMSAuthService.service.jwtService.JwtService;
import com.utiitsl.DMSAuthService.service.userService.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Value("${spring.oauth.success-url}")
    private String authSuccessUrl;

    @Value("${spring.oauth.failed-url}")
    private String oauthFailedUrl;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        try {

            OAuth2User oAuth2User =
                    (OAuth2User) authentication.getPrincipal();

            System.out.println("OAuth User Attributes: "
                    + oAuth2User.getAttributes());

            String email =
                    oAuth2User.getAttribute("email");

            String name =
                    oAuth2User.getAttribute("name");

            if (email == null) {
                throw new RuntimeException(
                        "Email not found from Google");
            }

            User user =
                    userService.findOrCreateOAuthUser(
                            email,
                            name
                    );

            String jwt =
                    jwtService.generateAccessToken(user);

            response.sendRedirect(authSuccessUrl+ jwt);

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(oauthFailedUrl);
        }
    }
}