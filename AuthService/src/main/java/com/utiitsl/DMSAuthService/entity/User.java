package com.utiitsl.DMSAuthService.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.utiitsl.DMSAuthService.constants.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String firstName;
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private String originalPassword; // ⚠️ REMOVE in production (security risk)

    @Enumerated(EnumType.STRING)
    private Role role;

    private String mobileNo;
    private String dateOfBirth;
    private String aadhaarNumber;
    private String panCard;

    @Column(name = "profile_image_name")
    private String profileImageName;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @CreationTimestamp
    private Date createdAt;
    private String createdBy;

    @UpdateTimestamp
    private Date updatedAt;
    private String updatedBy;

    @Column(nullable = false)
    private boolean activeStatus;

    // ✅ FIXED RELATIONSHIP
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private List<Address> addresses;

    // Refresh token mapping
    @OneToOne(mappedBy = "user")
    @ToString.Exclude
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