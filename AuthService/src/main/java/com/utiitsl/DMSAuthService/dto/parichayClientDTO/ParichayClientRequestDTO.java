package com.utiitsl.DMSAuthService.dto.parichayClientDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParichayClientRequestDTO {
    private String client_id;
    private String client_secret;
    private String redirect_uri;
    private String grant_type;
    private String code;
    private String code_verifier;

    private String scope;
    private Optional<String> state;
    private String code_challenge_method;
    private String code_challenge;
    private String response_type;

    private HttpStatus authorizationCode;
    private String accessToken;

}
