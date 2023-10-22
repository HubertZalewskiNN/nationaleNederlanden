package com.hz.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ACCOUNTS")
@Entity
public class AccountModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "APPLICATION_ID", nullable = false)
    private String applicationId;
    @NotBlank(message = "Name is mandatory")
    @Column(name = "NAME", nullable = false)
    private String name;
    @NotBlank(message = "Last name is mandatory")
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;
    @NotNull(message = "Ammount in PLN is mandatory")
    @Column(name = "BALANCE_PLN", nullable = false)
    private BigDecimal balancePln;
    @NotNull(message = "Ammount in USD is mandatory")
    @Column(name = "BALANCE_USD", nullable = false)
    private BigDecimal balanceUsd;
    public void setBalancePln(BigDecimal balancePln) {
        this.balancePln = balancePln.setScale(2, RoundingMode.DOWN);
    }

    public void setBalanceUsd(BigDecimal balanceUsd) {
        this.balanceUsd = balanceUsd.setScale(2, RoundingMode.DOWN);
    }
}
