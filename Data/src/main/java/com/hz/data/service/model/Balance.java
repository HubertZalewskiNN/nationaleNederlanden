package com.hz.data.service.model;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Balance {
    private String appId;
    private BigDecimal balancePln;
    private BigDecimal balanceUsd;
}
