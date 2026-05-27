package com.utiitsl.DMSAuthService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Logger")
@AllArgsConstructor
@Builder
public class Logger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long logId;

    @Column(name = "user_id", length = 255)
    private String userId;

    @Column(name = "location", nullable = false, length = 255)
    private String location;

    @Column(name = "ip_address", nullable = false, length = 50)
    private String ipAddress;

    @Column(name = "service_name", nullable = false, length = 255)
    private String serviceName;

    @Column(name = "service_details", columnDefinition = "TEXT")
    private String serviceDetails;

    @Column(name = "log_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT NOW()")
    private LocalDateTime logDatetime;

    @Column(name = "operation_type", length = 50)
    private String operationType;

    @Column(name = "http_method", length = 10)
    private String httpMethod;

    @Column(name = "response_status")
    private Integer responseStatus;

    @Column(name = "error_details", columnDefinition = "TEXT")
    private String errorDetails;

    @Column(name = "request_payload", columnDefinition = "TEXT")
    private String requestPayload;

    @Column(name = "response_payload", columnDefinition = "TEXT")
    private String responsePayload;

    @Column(name = "user_agent", length = 255)
    private String userAgent;

    @Column(name = "session_id", length = 255)
    private String sessionId;

    @Column(name = "latency")
    private Long latency;

    @Column(name = "severity_level", length = 20)
    private String severityLevel;

    @Column(name = "module_name", length = 255)
    private String moduleName;

    @Column(name = "geolocation", length = 255)
    private String geolocation;

    @Column(name = "auth_method", length = 50)
    private String authMethod;

    @Column(name = "event_id", length = 255)
    private String eventId;

    @Column(name = "is_sensitive")
    private Boolean isSensitive;
}
