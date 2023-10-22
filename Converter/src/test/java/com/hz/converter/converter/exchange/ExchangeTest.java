package com.hz.converter.converter.exchange;

import com.hz.converter.converter.client.AccountClient;
import com.hz.converter.converter.client.NbpExchangeClient;
import com.hz.converter.converter.client.exception.NbpServiceUnavailableException;
import com.hz.converter.converter.client.exception.UnsufficientFoundsExeption;
import com.hz.converter.converter.client.response.account.Account;
import com.hz.converter.converter.client.response.nbp.Rate;
import com.hz.converter.converter.controller.request.ExchangeEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.math.BigInteger;


@SpringBootTest
class ExchangeTest {

    @MockBean
    private AccountClient accountClient;
    @MockBean
    private NbpExchangeClient nbpExchangeClient;
    @Autowired
    private Exchange exchange;

    @Test
    void whenExchangePlnToUsd_thenCorrect() {
        Account account = Account
                .builder().appId("111").name("Jan").lastName("Kowalski")
                .balancePln(new BigDecimal(100.00)).balanceUsd(new BigDecimal(BigInteger.ZERO)).build();
        Mockito.when(accountClient.getAccount("111")).thenReturn(account);
        Mockito.when(nbpExchangeClient.getExchangeRateUSD()).thenReturn(Rate.builder().no("1").bid(4.1679).ask(4.2521).build());
        exchange.exchangeCurrency("111", new BigDecimal(100.00), ExchangeEnum.PLN2USD);
    }
    @Test
    void whenExchangeUsdToPln_thenCorrect() {
        Account account = Account
                .builder().appId("111").name("Jan").lastName("Kowalski")
                .balancePln(new BigDecimal(BigInteger.ZERO)).balanceUsd(new BigDecimal(100.00)).build();
        Mockito.when(accountClient.getAccount("111")).thenReturn(account);
        Mockito.when(nbpExchangeClient.getExchangeRateUSD()).thenReturn(Rate.builder().no("1").bid(4.1679).ask(4.2521).build());
        exchange.exchangeCurrency("111", new BigDecimal(100.00), ExchangeEnum.USD2PLN);
    }
    @Test
    void whenNotEnaughFound_thenError() {
        Account account = Account
                .builder().appId("111").name("Jan").lastName("Kowalski")
                .balancePln(new BigDecimal(BigInteger.ZERO)).balanceUsd(new BigDecimal(100.00)).build();
        Mockito.when(accountClient.getAccount("111")).thenReturn(account);
        Mockito.when(nbpExchangeClient.getExchangeRateUSD()).thenReturn(Rate.builder().no("1").bid(4.1679).ask(4.2521).build());
        UnsufficientFoundsExeption unsufficientFoundsExeption = Assertions.assertThrows(UnsufficientFoundsExeption.class,
                () -> exchange.exchangeCurrency("111", new BigDecimal(100.00), ExchangeEnum.PLN2USD));
        Assertions.assertEquals("111 does not have enough founds", unsufficientFoundsExeption.getMessage());
    }
    @Test
    void whenNbpReturnError_thenError() {
        Account account = Account
                .builder().appId("111").name("Jan").lastName("Kowalski")
                .balancePln(new BigDecimal(BigInteger.ZERO)).balanceUsd(new BigDecimal(100.00)).build();
        Mockito.when(accountClient.getAccount("111")).thenReturn(account);
        Mockito.when(nbpExchangeClient.getExchangeRateUSD()).thenThrow(new NbpServiceUnavailableException("NBP did not return rates"));
        NbpServiceUnavailableException nbpServiceUnavailableException = Assertions.assertThrows(NbpServiceUnavailableException.class,
                () -> exchange.exchangeCurrency("111", new BigDecimal(100.00), ExchangeEnum.USD2PLN));
        Assertions.assertEquals("NBP did not return rates", nbpServiceUnavailableException.getMessage());
    }

}