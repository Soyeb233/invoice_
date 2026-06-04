package com.utiitsl.DMSAuthService.service.authService;

import com.utiitsl.DMSAuthService.dto.AuthenticationResponseDTO;
import com.utiitsl.DMSAuthService.dto.LoginRequestDTO;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

//    AuthenticationResponseDTO register(RegisterRequestDTO registerRequest);

    AuthenticationResponseDTO login(LoginRequestDTO loginRequest);

    AuthenticationResponseDTO login(LoginRequestDTO loginRequest,HttpServletResponse response);


}
