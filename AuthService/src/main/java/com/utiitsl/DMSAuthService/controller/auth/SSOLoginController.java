package com.utiitsl.DMSAuthService.controller.auth;

import com.utiitsl.DMSAuthService.common.response.APIResponse;
import com.utiitsl.DMSAuthService.common.response.ResponseHandler;
import com.utiitsl.DMSAuthService.constants.LoggerMessage;
import com.utiitsl.DMSAuthService.dto.RegisterRequestDTO;
import com.utiitsl.DMSAuthService.service.userService.UserService;
import com.utiitsl.DMSAuthService.util.LoggerUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.utiitsl.DMSAuthService.constants.LoggerMessage.MODULE_NAME;

@RestController
public class SSOLoginController {


    @Autowired
    private UserService userService;




}
