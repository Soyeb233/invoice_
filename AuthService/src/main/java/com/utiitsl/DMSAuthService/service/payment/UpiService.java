package com.utiitsl.DMSAuthService.service.payment;

import org.springframework.stereotype.Service;

@Service
public class UpiService extends PaymentService{

    public String doPayment(){
        return "UPI PAYMENT";
    }
}
