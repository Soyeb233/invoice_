package com.utiitsl.DMSAuthService.controller.admin;

import com.utiitsl.DMSAuthService.constants.Role;
import com.utiitsl.DMSAuthService.service.payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/v1/admin")
public class AdminController {

    @Autowired
    @Qualifier("upiService")
    private PaymentService paymentService;

    @GetMapping("/adminMessage")
    public ResponseEntity<Object> getMessage(){
        System.err.println("PAYMENT SERVICE CALL :"+paymentService.doPayment());
        return new ResponseEntity<>(Arrays.asList(" ADMIN","ROLE ASSIGN "+ Role.SUPREMEADMIN+" , "+Role.SUPERADMIN+" ,"+Role.ADMIN.toString()), HttpStatus.OK) ;
    }
}
