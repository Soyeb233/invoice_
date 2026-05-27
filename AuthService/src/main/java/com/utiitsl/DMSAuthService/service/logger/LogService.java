package com.utiitsl.DMSAuthService.service.logger;

import com.utiitsl.DMSAuthService.dto.LogRequestDto;
public interface LogService {


     void saveLog(LogRequestDto logRequestDto);
}
