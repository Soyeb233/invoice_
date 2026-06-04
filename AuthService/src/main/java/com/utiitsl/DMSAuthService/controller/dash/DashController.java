package com.utiitsl.DMSAuthService.controller.dash;

import com.utiitsl.DMSAuthService.common.response.ResponseHandler;
import com.utiitsl.DMSAuthService.service.dashboard.DashboardService;
import com.utiitsl.DMSAuthService.service.userService.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashController {

    private final UserService userService;

    private final DashboardService dashboardService;

    @GetMapping("/")
    public ResponseEntity<?> getDashCount(){
        return ResponseHandler.generateResponse(dashboardService.fetchDashboardCount(),HttpStatus.OK,true);
    }


}
