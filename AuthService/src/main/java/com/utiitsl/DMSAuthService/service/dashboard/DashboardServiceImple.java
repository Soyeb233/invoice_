package com.utiitsl.DMSAuthService.service.dashboard;

import com.utiitsl.DMSAuthService.dto.dashboard.DashboardResponse;
import com.utiitsl.DMSAuthService.dto.dashboard.SalesAnalyticsDto;
import com.utiitsl.DMSAuthService.dto.dashboard.UserGrowthDto;
import com.utiitsl.DMSAuthService.service.file.FileService;
import com.utiitsl.DMSAuthService.service.userService.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImple implements DashboardService{

    private final FileService fileService;

    private final UserService userService;
    @Override
    public DashboardResponse fetchDashboardCount() {

        // TOTAL ACTIVE USER COUNT
        Long totalActiveUserCount= userService.findTotalActiveUserCount();
        Long totalFileUploadedCount=fileService.findTotalFileUploadCount();

        DashboardResponse response = new DashboardResponse();

        response.setTotalUsers(totalActiveUserCount);
        response.setTotalFileUpload(totalFileUploadedCount);
        response.setOrders(320);
        response.setTotalRevenue(120000);

        response.setUserGrowth(List.of(
                new UserGrowthDto("Jan", 30),
                new UserGrowthDto("Feb", 45),
                new UserGrowthDto("Mar", 60),
                new UserGrowthDto("Apr", 40),
                new UserGrowthDto("May", 90)
        ));

        response.setSalesAnalytics(List.of(
                new SalesAnalyticsDto("Jan", 40),
                new SalesAnalyticsDto("Feb", 35),
                new SalesAnalyticsDto("Mar", 70),
                new SalesAnalyticsDto("Apr", 55),
                new SalesAnalyticsDto("May", 80)
        ));

        return response;

    }
}
