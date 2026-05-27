package com.utiitsl.DMSAuthService.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogRequestDto {
    private String userId;
    private String location;
    private String ipAddress;
    private String serviceName;
    private String serviceDetails;
    private String operationType;
    private String httpMethod;
    private Integer responseStatus;
    private String errorDetails;
    private String requestPayload;
    private String responsePayload;
    private String userAgent;
    private String sessionId;
    private Long latency;
    private String severityLevel;
    private String moduleName;
    private String geolocation;
    private String authMethod;
    private String eventId;
    private Boolean isSensitive;

    @Override
    public String toString() {
        return "LogRequestDto{" +
                "userId='" + userId + '\'' +
                ", location='" + location + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", serviceDetails='" + serviceDetails + '\'' +
                ", operationType='" + operationType + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                ", responseStatus=" + responseStatus +
                ", errorDetails='" + errorDetails + '\'' +
                ", requestPayload='" + requestPayload + '\'' +
                ", responsePayload='" + responsePayload + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", latency=" + latency +
                ", severityLevel='" + severityLevel + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", geolocation='" + geolocation + '\'' +
                ", authMethod='" + authMethod + '\'' +
                ", eventId='" + eventId + '\'' +
                ", isSensitive=" + isSensitive +
                '}';
    }
}
