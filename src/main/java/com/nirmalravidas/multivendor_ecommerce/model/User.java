package com.nirmalravidas.multivendor_ecommerce.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nirmalravidas.multivendor_ecommerce.enums.USER_ROLE;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(nullable = false)
    private String fullName;

    private String mobile;

    @Enumerated(EnumType.STRING)
    private USER_ROLE role;

    @OneToMany
    private Set<Address> addresses = new HashSet<>();
}
