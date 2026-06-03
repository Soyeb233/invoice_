package com.utiitsl.DMSAuthService.entity;

import jakarta.persistence.*;
import lombok.*;
import org.checkerframework.checker.units.qual.A;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "states")
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "state_code", nullable = false)
    private String stateCode;

    @Column(name = "state_name", nullable = false, unique = true)
    private String stateName;

    @OneToMany(mappedBy = "state", cascade = CascadeType.ALL)
    private List<District> districts;


}
