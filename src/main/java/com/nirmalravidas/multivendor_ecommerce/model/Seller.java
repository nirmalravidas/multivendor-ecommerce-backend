package com.nirmalravidas.multivendor_ecommerce.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nirmalravidas.multivendor_ecommerce.enums.AccountStatus;
import com.nirmalravidas.multivendor_ecommerce.enums.USER_ROLE;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sellerName;

    private String mobile;

    @Column(unique = true, nullable = false)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Embedded
    private BusinessDetails businessDetails = new BusinessDetails();

    @Embedded
    private BankDetails bankDetails = new BankDetails();

    private String GSTIN;

    @Enumerated(EnumType.STRING)
    private USER_ROLE role;

    private AccountStatus accountStatus = AccountStatus.PENDING_VERIFICATION;

}
