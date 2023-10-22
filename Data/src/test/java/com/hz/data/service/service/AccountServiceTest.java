package com.hz.data.service.service;

import com.hz.data.service.controller.exceptions.AccountNotFoundExeption;
import com.hz.data.service.controller.exceptions.MissingBalanceException;
import com.hz.data.service.model.Account;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

@SpringBootTest
class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Test
    void whenCreateAccount_thenCorrectAnwser() {
        Account account = Account.builder()
                .name("Jan")
                .lastName("Kowalski")
                .balancePln(new BigDecimal(1000.00))
                .build();
        String appId = accountService.createAccount(account);
        assertThat(appId != null);
        assertThat(!appId.isEmpty());

    }

    @Test
    void whenCreateAccountWithNoName_thenValidationEx() {
        Account account = Account.builder()
                .lastName("Kowalski")
                .balancePln(new BigDecimal(1000.00))
                .build();
        ConstraintViolationException constraintViolationException = Assertions.assertThrows(ConstraintViolationException.class,
                () -> accountService.createAccount(account));
        Assertions.assertEquals("Name is mandatory", constraintViolationException.getConstraintViolations().stream().findFirst().get().getMessage());
    }

    @Test
    void whenGetAccount_thenOk() {
        Account account = accountService.getAccount("11aaa");
        assertThat(account != null);
        assertThat(account.getName().equals("Hubert"));
    }

    @Test
    void whenGetAccountEmpty_thenError() {
        AccountNotFoundExeption accountNotFoundExeption = Assertions.assertThrows(AccountNotFoundExeption.class,
                () -> accountService.getAccount("50"));
        Assertions.assertEquals("Account appId = 50 does not exists or are more then one", accountNotFoundExeption.getMessage());
    }

    @Test
    void whenGetAccountAppIdNull_thenError() {
        AccountNotFoundExeption accountNotFoundExeption = Assertions.assertThrows(AccountNotFoundExeption.class,
                () -> accountService.getAccount(null));
        Assertions.assertEquals("Account appId = null does not exists or are more then one", accountNotFoundExeption.getMessage());
    }

    @Test
    void whenUpdateBalance_thenOk() {
        accountService.updateBalance("11aaa", new BigDecimal(111000.00), new BigDecimal(999.01));
        Account account = accountService.getAccount("11aaa");
        assertThat(account != null);
        assertThat(account.getBalancePln().equals(new BigDecimal(111000.00)));
        assertThat(account.getBalanceUsd().equals(new BigDecimal(999.01)));

    }
    @Test
    void whenNoBalance_whenError() {
        MissingBalanceException missingBalanceException = Assertions.assertThrows(MissingBalanceException.class,
                () -> accountService.updateBalance("11aaa", null, null));
        Assertions.assertEquals("PLN or USD ammount are null", missingBalanceException.getMessage());
        ;
    }

}