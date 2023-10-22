package com.hz.data.service.model;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Account {

    private String appId;
    private String name;
    private String lastName;
    private BigDecimal balancePln;
    private BigDecimal balanceUsd;
}
