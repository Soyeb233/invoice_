package com.utiitsl.DMSAuthService.dto.parichayClientDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParichayUserDetailsDTO {

    private String userName;

    @JsonProperty("FirstName")
    private String firstName;
    @JsonProperty("LastName")
    private String lastName;

    @JsonProperty("EmailId")
    private String emailId;
    @JsonProperty("Email")
    private String email;

    @JsonProperty("MobileNo")
    private String mobileNo;
    @JsonProperty("Gender")
    private String gender;

    private String designation;
    private String ua;
    private String status;
    private String userId;
    private String parichayId;
    private String ip;

    private String browserId;
    private String sessionId;
    private String localTokenId;
    private String loginId;
    private String employeeCode;
    private String zimOtp;
    private String departmentName;
    private List<String> mailEquivalentAddress;

    private String mailAlternateAddress;
    private String city;
    private String state;
    private String location;
    private String country;

    private String nicaccountexpirydate;
    private String expiresAt;
    private String role;
    private String dor;
    private String brwId;

}
