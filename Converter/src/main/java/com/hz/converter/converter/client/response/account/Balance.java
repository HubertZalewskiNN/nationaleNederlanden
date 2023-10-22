package com.hz.converter.converter.client.response.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Balance {
    private String appId;
    private BigDecimal balancePln;
    private BigDecimal balanceUsd;
}
