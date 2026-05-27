package com.utiitsl.DMSAuthService.dto;

import com.utiitsl.DMSAuthService.constants.Role;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDTO implements Serializable {
    private int id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String originalPassword;
    private Role role;
    private Boolean activeStatus;
}
