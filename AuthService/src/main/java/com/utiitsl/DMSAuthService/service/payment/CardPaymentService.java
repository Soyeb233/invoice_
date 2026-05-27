package com.utiitsl.DMSAuthService.service.payment;

import org.springframework.stereotype.Service;

@Service
public class CardPaymentService extends PaymentService{

    public String doPayment(){
        return "CARD PAYMENT";
    }
}
