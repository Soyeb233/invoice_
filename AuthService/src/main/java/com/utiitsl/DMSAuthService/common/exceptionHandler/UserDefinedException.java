package com.utiitsl.DMSAuthService.common.exceptionHandler;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDefinedException extends RuntimeException{

    private String message;
    private HttpStatus status;

}
