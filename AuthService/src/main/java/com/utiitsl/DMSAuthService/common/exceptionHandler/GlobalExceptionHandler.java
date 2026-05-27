package com.utiitsl.DMSAuthService.common.exceptionHandler;

import com.utiitsl.DMSAuthService.common.response.APIResponse;
import com.utiitsl.DMSAuthService.common.response.ResponseHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleCheckedException(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
        System.err.println(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<Object> handleNullPointerException(NullPointerException exception) {
        System.err.println(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }

    @ExceptionHandler(UserDefinedException.class)
    public ResponseEntity<APIResponse> userDefinedException(UserDefinedException userDefinedException){
        return ResponseHandler.generateResponse(userDefinedException.getMessage(),userDefinedException.getStatus(),false);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<APIResponse> exceesDeniedException(AccessDeniedException deniedException){
        return ResponseHandler.generateResponse("CANNOT ACCESS URL YOU ARE RESTRICTED", HttpStatus.FORBIDDEN,false);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<APIResponse> userDefinedException(BadCredentialsException badCredentialsException){
        return ResponseHandler.generateResponse("USER OR PASSWORD NOT MATCH", HttpStatus.FORBIDDEN,false);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<APIResponse> userIsDisableException(DisabledException disabledException){
        return ResponseHandler.generateResponse("USER is not Active",HttpStatus.FORBIDDEN,false);
    }

}
