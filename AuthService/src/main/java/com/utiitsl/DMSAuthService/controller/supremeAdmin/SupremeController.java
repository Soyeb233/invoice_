package com.utiitsl.DMSAuthService.controller.supremeAdmin;

import com.utiitsl.DMSAuthService.constants.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/v1/supreme")
public class SupremeController {

    @GetMapping("/getMessage")
    public ResponseEntity<Object> getMessage(){
        return new ResponseEntity<>(Arrays.asList("SUPER ADMIN","ROLE ASSIGN "+ Role.SUPREMEADMIN.name()), HttpStatus.OK) ;
    }
}
