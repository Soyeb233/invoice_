package com.utiitsl.DMSAuthService.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesAnalyticsDto {

    private String month;
    private long sales;

}
