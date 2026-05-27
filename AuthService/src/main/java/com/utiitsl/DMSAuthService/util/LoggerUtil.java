package com.utiitsl.DMSAuthService.util;

import com.utiitsl.DMSAuthService.dto.LogRequestDto;
import com.utiitsl.DMSAuthService.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LoggerUtil {




    public static LogRequestDto buildLogRequestDto(
            HttpServletRequest request,
            String userId,
//            String serviceName,
            String serviceDetails,
//            String operationType,
//            String requestPayload,
//            String responsePayload,
//            Integer responseStatus,
//            Long latency,
//            String severityLevel,
            String moduleName,
            String geoLocation,
            String errorDetails,
            Boolean isSensitive
    ) {

        return LogRequestDto.builder()
                .userId(userId)
                .location(request.getRemoteAddr()) // Or use a geolocation API if required
                .ipAddress(request.getRemoteAddr())
                .serviceName(request.getRequestURI())
                .serviceDetails(serviceDetails)
                .operationType(request.getMethod())
                .httpMethod(request.getMethod())
//                .responseStatus(responseStatus)
                .errorDetails(errorDetails) // Populate if there's an error
                .requestPayload("")
//                .responsePayload(responsePayload)
                .userAgent(request.getHeader("User-Agent"))
                .sessionId(request.getSession() != null ? request.getSession().getId() : null)
//                .latency(latency)
//                .severityLevel(severityLevel)
                .moduleName(moduleName)
                .geolocation(geoLocation)
//                .authMethod(authMethod)
//                .eventId(eventId)
                .isSensitive(isSensitive)
                .build();
    }


}