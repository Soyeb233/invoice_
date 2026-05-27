package com.utiitsl.DMSAuthService.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse {

    private Object data;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")  // Use the desired date format
    private LocalDateTime localDateTime;
    private boolean isSuccess;
    private HttpStatus status;
}

