package com.utiitsl.DMSAuthService.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class ResponseHandler {

    public static ResponseEntity<APIResponse> generateResponse(Object object, HttpStatus status, boolean isSuccess){
        return new ResponseEntity<>(new APIResponse(object, LocalDateTime.now(),isSuccess,status),status);
    }
}
