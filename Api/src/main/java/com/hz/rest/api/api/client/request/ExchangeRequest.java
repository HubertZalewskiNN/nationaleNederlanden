package com.hz.rest.api.api.client.request;

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
