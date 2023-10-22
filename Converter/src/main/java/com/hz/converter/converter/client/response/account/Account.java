package com.hz.converter.converter.client.response.account;

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
