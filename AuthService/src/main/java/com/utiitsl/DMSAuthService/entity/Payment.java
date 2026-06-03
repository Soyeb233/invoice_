package com.utiitsl.DMSAuthService.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private UserSubscription subscription;

    private Double amount;
    private String currency;

    private String paymentStatus;
    private String paymentMethod;
    private String transactionId;

    private Date paidAt;
}