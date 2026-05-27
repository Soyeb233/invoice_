package com.utiitsl.DMSAuthService.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.utiitsl.DMSAuthService.constants.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Table(name = "Users")
public class User implements UserDetails, Serializable {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;

    @NonNull
    @Column(nullable = false, unique = true)
    private String email;

    private String firstName;
    private String lastName;


    @Column(nullable = false)
    private String originalPassword;

    @Column(name = "activeStatus", nullable = false)
    private boolean activeStatus;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "mobileNo")
    private String mobileNo;

    @OneToOne(mappedBy = "user")
    @ToString.Exclude // Exclude to prevent recursion
    private RefreshToken refreshToken;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority("ROLE_"+role.name()));
        return role.getAuthorities();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
            return activeStatus;
    }
}
