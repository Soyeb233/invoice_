package com.utiitsl.DMSAuthService.entity;

import com.utiitsl.DMSAuthService.constants.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private SubscriptionPlan plan;

    private LocalDate startDate;

    private LocalDate endDate;

    // ACTIVE, EXPIRED, CANCELLED
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;


    private Boolean autoRenew = true;

    // getters and setters
}