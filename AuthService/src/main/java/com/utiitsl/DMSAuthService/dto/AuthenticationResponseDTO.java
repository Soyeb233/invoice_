package com.utiitsl.DMSAuthService.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AuthenticationResponseDTO {

    private String token;
    private String refreshToken;
    private HttpStatus status;
    private String role;
    private String username;
    private boolean isAuthenticated;
    private Long tokenExpire;
}
