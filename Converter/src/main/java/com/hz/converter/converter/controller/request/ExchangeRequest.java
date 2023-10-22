package com.hz.converter.converter.controller.request;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ExchangeRequest {
    private String appId;
    private BigDecimal ammount;
    private ExchangeEnum exchange;
}
