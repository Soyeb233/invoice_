package com.utiitsl.DMSAuthService.dto.dashboard;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponse {

    private long totalUsers;
    private long totalFileUpload;
    private long orders;
    private double totalRevenue;

    private List<UserGrowthDto> userGrowth;
    private List<SalesAnalyticsDto> salesAnalytics;
}
