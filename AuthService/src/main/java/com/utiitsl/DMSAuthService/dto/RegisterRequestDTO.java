package com.utiitsl.DMSAuthService.dto;

import com.utiitsl.DMSAuthService.constants.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegisterRequestDTO {

    private String email;
    private String firstName;
    private String lastName;
    private String mobileNo;
    private String username;
    private String password;
    private Role role;
}
