package com.hz.converter.converter.client.response.nbp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NbpExchangeRate {

    private String table;
    private String currency;
    private String code;
    private List<Rate> rates;

}
