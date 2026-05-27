package com.utiitsl.DMSAuthService.controller.dash;

import com.utiitsl.DMSAuthService.common.response.APIResponse;
import com.utiitsl.DMSAuthService.common.response.ResponseHandler;
import com.utiitsl.DMSAuthService.dto.UserDTO;
import com.utiitsl.DMSAuthService.service.userService.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/dash")
@RequiredArgsConstructor
public class DashController {

    private final UserService userService;


    @GetMapping("/getUserById/{id}")
//    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<APIResponse> getUserById(@PathVariable Integer id){
       return ResponseHandler.generateResponse(userService.getUserById(id), HttpStatus.ACCEPTED,true);
    }

    @GetMapping("/getUserByUsername/{username}")
    public ResponseEntity<APIResponse> getUserByUsername(@PathVariable String username){
        return ResponseHandler.generateResponse(userService.getUserByUsername(username),HttpStatus.ACCEPTED,true);
    }

    @PutMapping("/updateUser/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<APIResponse> updateUser(@RequestBody UserDTO userDTO,@PathVariable Integer id){
        return ResponseHandler.generateResponse(userService.updateUser(userDTO,id),HttpStatus.NO_CONTENT,true );
    }


    @DeleteMapping("/deleteUserById")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<APIResponse> deleteUserById(@RequestParam(name = "id") Integer id){
        return ResponseHandler.generateResponse(userService.deleteUser(id),HttpStatus.OK,true);
    }


}
