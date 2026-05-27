package com.utiitsl.DMSAuthService.controller.user;

import com.utiitsl.DMSAuthService.common.response.APIResponse;
import com.utiitsl.DMSAuthService.common.response.ResponseHandler;
import com.utiitsl.DMSAuthService.constants.LoggerMessage;
import com.utiitsl.DMSAuthService.constants.Role;
import com.utiitsl.DMSAuthService.dto.RegisterRequestDTO;
import com.utiitsl.DMSAuthService.dto.UserDTO;
import com.utiitsl.DMSAuthService.repository.UserRepository;
import com.utiitsl.DMSAuthService.service.authService.AuthService;
import com.utiitsl.DMSAuthService.service.logger.LogService;
import com.utiitsl.DMSAuthService.service.userService.UserService;
import com.utiitsl.DMSAuthService.util.LoggerUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.utiitsl.DMSAuthService.constants.LoggerMessage.MODULE_NAME;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class UserController {

    private final AuthService authService;
    private final UserService userService;
    private final LogService loggerService;
    private final Logger logger= LoggerFactory.getLogger(UserController.class);


    @PreAuthorize("hasRole('ROLE_APPLICATION_ADMIN') or hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<APIResponse> register(@RequestBody RegisterRequestDTO registerRequest, @RequestParam(value = "geoLocation", required = false) String geoLocation,
                                                HttpServletRequest request){
        log.info(LoggerUtil.buildLogRequestDto(
                request,
                registerRequest.getUsername(),
                LoggerMessage.SERVICE_NAME_TEMPLATE.replace("{}", "New User creation request with username : "+ registerRequest.getUsername()),
                MODULE_NAME,
                geoLocation,
                null,
                false
        ).toString());
        loggerService.saveLog(LoggerUtil.buildLogRequestDto(
                request,
                registerRequest.getUsername(),
                LoggerMessage.SERVICE_NAME_TEMPLATE.replace("{}", "New User creation request with username : "+ registerRequest.getUsername()),
                MODULE_NAME,
                geoLocation,
                null,
                false
        ));
        return ResponseHandler.generateResponse(userService.createUser(registerRequest),HttpStatus.OK,true);
    }

    @PreAuthorize("hasRole('ROLE_APPLICATION_ADMIN') or hasRole('ROLE_ADMIN')")
    @GetMapping("/getAllUser")
    public ResponseEntity<APIResponse> getAllUser(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size,
                                              @RequestParam(value = "geoLocation", required = false) String geoLocation,
                                              HttpServletRequest request){
        logger.info("FETCH ALL USER");
        logger.info(LoggerUtil.buildLogRequestDto(
                request,
                LoggerMessage.SERVICE_NAME_TEMPLATE.replace("{}", "Login request with username "),
                MODULE_NAME,
                geoLocation,
                null,
                "",
                false
        ).toString());
        log.info(LoggerUtil.buildLogRequestDto(
                request,
                LoggerMessage.SERVICE_NAME_TEMPLATE.replace("{}", "Login request with username "),
                MODULE_NAME,
                geoLocation,
                null,
                "",
                false
        ).toString());
        return ResponseHandler.generateResponse(userService.getAllUser(page,size),HttpStatus.OK,true);
    }

    @PreAuthorize("hasRole('ROLE_APPLICATION_ADMIN') or hasRole('ROLE_ADMIN')")
    @PostMapping("/activateDeactivateUser/{username}/{status}")
    public ResponseEntity<APIResponse> activateDeactivateUser(@PathVariable String username,@PathVariable boolean status){
        System.err.println("USERNAME :"+username +"  STATUS :"+status);
        return ResponseHandler.generateResponse(userService.activateDeactivateUser(username,status),HttpStatus.OK,true);
    }


    @PutMapping("/editUser/{userId}")
    public ResponseEntity<APIResponse> editUser(@PathVariable Integer userId,@RequestBody UserDTO userDTO){
        System.err.println("ID OF USER P "+userId);
        System.err.println("USER DATA :");
        System.err.println(userDTO);
        return ResponseHandler.generateResponse(userService.updateUser(userDTO,userId),HttpStatus.OK,true);
    }

}
