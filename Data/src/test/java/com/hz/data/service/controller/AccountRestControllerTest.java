package com.hz.data.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hz.data.service.service.AccountService;
import com.hz.data.service.model.Account;
import com.hz.data.service.model.Balance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;


@WebMvcTest(AccountRestController.class)
class AccountRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    void whenCreate_thenOk() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Account account = Account.builder().name("Jan")
                        .lastName("Kowalski")
                                .balancePln(new BigDecimal(10.00))
                                        .build();
                mockMvc.perform(post("/accountDao/account/create")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(account)))
                .andExpect(status().isOk());
    }

    @Test
    void whenEmptyAccount_thenError() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Account account = null;
        mockMvc.perform(post("/accountDao/account/create")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(account)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void whenUpdateBalance_thenOk() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Balance balance = Balance.builder().appId("11aaa")
                .balancePln(new BigDecimal(100.00))
                .balanceUsd(new BigDecimal(10.00))
                .build();
        mockMvc.perform(put("/accountDao/updateBalance")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(balance)))
                .andExpect(status().isOk());
    }
    @Test
    void whenEmptyBalance_thenError() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Balance balance = null;
        mockMvc.perform(put("/accountDao/updateBalance")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(balance)))
                .andExpect(status().is4xxClientError());
    }
    @Test
    void whenGetAccount_thenOk() throws Exception {
        mockMvc.perform(get("/accountDao/account/{appId}", "11aaa")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }
    @Test
    void whenGetAccountEmptyAppId_thenError() throws Exception {
        mockMvc.perform(get("/accountDao/account/{appId}", "")
                        .contentType("application/json"))
                .andExpect(status().is4xxClientError());
    }
}