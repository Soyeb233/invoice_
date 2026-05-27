package com.utiitsl.DMSAuthService.service.logger;

import com.utiitsl.DMSAuthService.dto.LogRequestDto;
import com.utiitsl.DMSAuthService.entity.Logger;
import com.utiitsl.DMSAuthService.repository.LoggerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LoggerServiceImpl implements LogService {

    private final LoggerRepository loggerRepository;

    @Override
    public void saveLog(LogRequestDto logRequestDto) {
            Logger log = new Logger();
            log.setUserId(logRequestDto.getUserId());
            log.setLocation(logRequestDto.getLocation());
            log.setIpAddress(logRequestDto.getIpAddress());
            log.setServiceName(logRequestDto.getServiceName());
            log.setServiceDetails(logRequestDto.getServiceDetails());
            log.setLogDatetime(LocalDateTime.now());
            log.setOperationType(logRequestDto.getOperationType());
            log.setHttpMethod(logRequestDto.getHttpMethod());
            log.setResponseStatus(logRequestDto.getResponseStatus());
            log.setErrorDetails(logRequestDto.getErrorDetails());
            log.setRequestPayload(logRequestDto.getRequestPayload());
            log.setResponsePayload(logRequestDto.getResponsePayload());
            log.setUserAgent(logRequestDto.getUserAgent());
            log.setSessionId(logRequestDto.getSessionId());
            log.setLatency(logRequestDto.getLatency());
            log.setSeverityLevel(logRequestDto.getSeverityLevel());
            log.setModuleName(logRequestDto.getModuleName());
            log.setGeolocation(logRequestDto.getGeolocation());
            log.setAuthMethod(logRequestDto.getAuthMethod());
            log.setEventId(logRequestDto.getEventId());
            log.setIsSensitive(logRequestDto.getIsSensitive());
            loggerRepository.save(log);
        }
    }

