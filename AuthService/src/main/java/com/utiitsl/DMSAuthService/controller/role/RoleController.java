package com.utiitsl.DMSAuthService.controller.role;

import com.utiitsl.DMSAuthService.common.response.APIResponse;
import com.utiitsl.DMSAuthService.common.response.ResponseHandler;
import com.utiitsl.DMSAuthService.constants.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/role")
public class RoleController {

    @RequestMapping("/getRole")
    public ResponseEntity<APIResponse> getRole(){

        // Convert enum values to list of strings
        List<String> statusValues = Arrays.stream(Role.values())
                .map(Enum::name)  // Convert enum to string
                .collect(Collectors.toList());
        return ResponseHandler.generateResponse(statusValues, HttpStatus.OK,true);
    }
}
