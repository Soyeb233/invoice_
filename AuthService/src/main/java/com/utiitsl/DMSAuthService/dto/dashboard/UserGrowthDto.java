package com.utiitsl.DMSAuthService.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGrowthDto {

    private String month;
    private long users;
}