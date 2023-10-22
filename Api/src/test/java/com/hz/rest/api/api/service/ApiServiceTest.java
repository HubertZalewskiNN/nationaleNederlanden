package com.hz.rest.api.api.service;

import com.hz.rest.api.api.client.AccountClient;
import com.hz.rest.api.api.client.ConverterClient;
import com.hz.rest.api.api.client.exception.ConverterServiceUnavailableException;
import com.hz.rest.api.api.client.request.Account;
import com.hz.rest.api.api.client.request.ExchangeEnum;
import com.hz.rest.api.api.client.request.ExchangeRequest;
import com.hz.rest.api.api.controller.exception.NoAccountException;
import com.hz.rest.api.api.controller.exception.NoAppIdException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApiServiceTest {

    @MockBean
    private AccountClient accountClient;
    @MockBean
    private ConverterClient converterClient;
    @Autowired
    private ApiService apiService;

    @Test
    void whentCreate_thenOk() {
        Account account = Account
                .builder().name("Jan").lastName("Kowalski")
                .balancePln(new BigDecimal(100.00)).build();
        Mockito.when(accountClient.createAccount(account)).thenReturn(UUID.randomUUID().toString());
        String appId = apiService.createAccount(account);
        Assertions.assertNotEquals(appId, null);
        Assertions.assertNotEquals(appId, "");
    }

    @Test
    void whenCreateEmptyAccount_thenError() {
        NoAccountException noAccountException = Assertions.assertThrows(NoAccountException.class,
                () -> apiService.createAccount(null));
        Assertions.assertEquals("No account to create", noAccountException.getMessage());
    }

    @Test
    void whenGetAccount_thenOk() {
        Account account = Account
                .builder()
                .appId("111").name("Jan").lastName("Kowalski")
                .balancePln(new BigDecimal(100.00))
                .balanceUsd(new BigDecimal(0.00)).build();
        Mockito.when(accountClient.getAccount("111")).thenReturn(account);
        Assertions.assertEquals(apiService.getAccount("111").getName(), "Jan");

    }

    @Test
    void whenGetAccountNoAppId_thenError() {
        NoAppIdException noAppIdException = Assertions.assertThrows(NoAppIdException.class,
                () -> apiService.getAccount(""));
        Assertions.assertEquals("Application ID not found", noAppIdException.getMessage());
    }

    @Test
    void whenGetAccount_thenNoAccount() {
        Mockito.when(accountClient.getAccount("111")).thenReturn(null);
        NoAccountException noAccountException = Assertions.assertThrows(NoAccountException.class,
                () -> apiService.getAccount("111"));
        Assertions.assertEquals("Account was not found", noAccountException.getMessage());

    }
    @Test
    void whenExchange_thenError() {

        NoAppIdException noAppIdException = Assertions.assertThrows(NoAppIdException.class,
                () -> apiService.exchange(ExchangeRequest.builder().ammount(new BigDecimal(100.00)).exchange(ExchangeEnum.PLN2USD).build()));
        Assertions.assertEquals("Application ID not found", noAppIdException.getMessage());
    }
}