package com.utiitsl.DMSAuthService.entity;

import com.utiitsl.DMSAuthService.constants.BillingCycle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // e.g. BASIC, PRO, ENTERPRISE

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private BillingCycle billingCycle; // MONTHLY, YEARLY

    private String description;

    private Boolean isActive = true;

    // getters and setters
}