package com.utiitsl.DMSAuthService.service.authService;

import com.utiitsl.DMSAuthService.dto.AuthenticationResponseDTO;
import com.utiitsl.DMSAuthService.dto.LoginRequestDTO;
import com.utiitsl.DMSAuthService.dto.RegisterRequestDTO;
import com.utiitsl.DMSAuthService.dto.UserDTO;
import com.utiitsl.DMSAuthService.entity.User;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

//    AuthenticationResponseDTO register(RegisterRequestDTO registerRequest);

    AuthenticationResponseDTO login(LoginRequestDTO loginRequest);

    AuthenticationResponseDTO login(LoginRequestDTO loginRequest,HttpServletResponse response);


}
