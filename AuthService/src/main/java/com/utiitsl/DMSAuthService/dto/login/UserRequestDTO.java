package com.utiitsl.DMSAuthService.dto.login;

import com.utiitsl.DMSAuthService.constants.Role;
import com.utiitsl.DMSAuthService.dto.address.AddressDTO;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;

    private String mobileNo;
    private String dateOfBirth;
    private String aadhaarNumber;
    private String panCard;

    private Role role;

    private List<AddressDTO> addresses;
}