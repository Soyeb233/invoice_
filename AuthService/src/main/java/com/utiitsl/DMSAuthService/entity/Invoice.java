package com.utiitsl.DMSAuthService.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String invoiceNumber;

    @ManyToOne
    private User user;

    @OneToOne
    private Payment payment;

    private Double amount;
    private Double taxAmount;
    private Double totalAmount;

    private String pdfUrl;

    private LocalDateTime issuedAt;

    private Boolean sentViaEmail;
    private Boolean sentViaWhatsapp;
}