package com.utiitsl.DMSAuthService.controller.superAdmin;

import com.utiitsl.DMSAuthService.constants.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/v1/superAdmin")
public class SuperAdminController {

    @GetMapping("/sAdminMessage")
    public ResponseEntity<Object> getMessage(){
        return new ResponseEntity<>(Arrays.asList("SUPER ADMIN","ROLE ASSIGN "+ Role.SUPREMEADMIN.name()+"  "+Role.SUPERADMIN), HttpStatus.OK) ;
    }
}
