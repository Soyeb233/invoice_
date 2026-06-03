package com.utiitsl.DMSAuthService.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utiitsl.DMSAuthService.common.response.APIResponse;
import com.utiitsl.DMSAuthService.common.response.ResponseHandler;
import com.utiitsl.DMSAuthService.constants.LoggerMessage;
import com.utiitsl.DMSAuthService.constants.Role;
import com.utiitsl.DMSAuthService.dto.RegisterRequestDTO;
import com.utiitsl.DMSAuthService.dto.UserDTO;
import com.utiitsl.DMSAuthService.dto.login.UserRequestDTO;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

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

    private final ObjectMapper objectMapper;

    // CREATE USER WITH PROFILE PIC
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createUser(
            @RequestPart("user") String userJson,
            @RequestPart(value = "image", required = false) MultipartFile image)
            throws Exception {

        UserRequestDTO dto =
                objectMapper.readValue(userJson, UserRequestDTO.class);

        return ResponseHandler.generateResponse( userService.createUser(dto, image),HttpStatus.CREATED,true);

    }

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

    @PreAuthorize("hasRole('ROLE_APPLICATION_ADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
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
