package com.hz.converter.converter.client.response.nbp;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rate {
    private String no;
    private Date effectiveDate;
    private double bid;
    private double ask;
}
