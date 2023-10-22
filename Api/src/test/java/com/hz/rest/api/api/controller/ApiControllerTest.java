package com.hz.rest.api.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hz.rest.api.api.client.request.Account;
import com.hz.rest.api.api.client.request.ExchangeEnum;
import com.hz.rest.api.api.client.request.ExchangeRequest;
import com.hz.rest.api.api.service.ApiService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApiController.class)
class ApiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ApiService apiService;


    @Test
    void whenExchange_thenOk() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ExchangeRequest exchangeRequest = ExchangeRequest.builder().appId("111")
                .ammount(new BigDecimal(100.00)).exchange(ExchangeEnum.PLN2USD).build();
        mockMvc.perform(put("/api/account/convert")
                        .contentType("application/json")
                        .header("API-TOKEN", "TOKEN123")
                        .content(mapper.writeValueAsString(exchangeRequest)))
                .andExpect(status().isOk());
    }
    @Test
    void whenExchangeNoToken_thenError() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ExchangeRequest exchangeRequest = ExchangeRequest.builder().appId("111")
                .ammount(new BigDecimal(100.00)).exchange(ExchangeEnum.PLN2USD).build();
        mockMvc.perform(put("/api/account/convert")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(exchangeRequest)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void whenExchangeNoAppId_thenError() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ExchangeRequest exchangeRequest = ExchangeRequest.builder()
                .ammount(new BigDecimal(100.00)).exchange(ExchangeEnum.PLN2USD).build();
        mockMvc.perform(put("/api/account/convert")
                        .contentType("application/json")
                        .header("API-TOKEN", "TOKEN123")
                        .content(mapper.writeValueAsString(exchangeRequest)))
                .andExpect(status().is4xxClientError());
    }
    @Test
    void whenCreate_thenOk() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Account account = Account.builder().name("Jan").lastName("Kowalski").balancePln(new BigDecimal(100.00)).build();
        mockMvc.perform(post("/api/create")
                        .contentType("application/json")
                        .header("API-TOKEN", "TOKEN123")
                        .content(mapper.writeValueAsString(account)))
                .andExpect(status().isOk());
    }
    @Test
    void whenCreateNoToken_thenError() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Account account = Account.builder().name("Jan").lastName("Kowalski").balancePln(new BigDecimal(100.00)).build();
        mockMvc.perform(post("/api/create")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(account)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void whenGetAccount_thenOk() throws Exception {

        mockMvc.perform(get("/api/account/{appId}", "111")
                        .contentType("application/json")

                        .header("API-TOKEN", "TOKEN123"))
                .andExpect(status().isOk());
    }
    @Test
    void whenGetAccountNoAppId_thenError() throws Exception {

        mockMvc.perform(get("/api/account/{appId}", "")
                        .contentType("application/json")

                        .header("API-TOKEN", "TOKEN123"))
                .andExpect(status().is4xxClientError());
    }
    @Test
    void whenGetAccountNoToken_thenError() throws Exception {

        mockMvc.perform(get("/api/account/{appId}", "111")
                        .contentType("application/json"))

                .andExpect(status().is4xxClientError());
    }


}