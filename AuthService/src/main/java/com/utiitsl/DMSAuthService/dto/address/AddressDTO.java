package com.utiitsl.DMSAuthService.dto.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private Long stateId;
    private Long districtId;
    private String city;
}