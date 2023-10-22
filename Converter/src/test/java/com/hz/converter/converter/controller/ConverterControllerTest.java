package com.hz.converter.converter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hz.converter.converter.controller.request.ExchangeEnum;
import com.hz.converter.converter.controller.request.ExchangeRequest;
import com.hz.converter.converter.exchange.Exchange;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConverterController.class)
class ConverterControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private Exchange exchange;

    @Test
    void whenExchange_thenOk() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ExchangeRequest exchangeRequest = ExchangeRequest.builder().appId("111")
                        .ammount(new BigDecimal(100.00)).exchange(ExchangeEnum.PLN2USD).build();
        mockMvc.perform(get("/converter")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(exchangeRequest)))
                .andExpect(status().isOk());
    }
    @Test
    void whenExchangeAmmountEmpty_thenError() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ExchangeRequest exchangeRequest = ExchangeRequest.builder().appId("111")
                .exchange(ExchangeEnum.PLN2USD).build();
        mockMvc.perform(get("/converter")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(exchangeRequest)))
                .andExpect(status().is4xxClientError());
    }

}