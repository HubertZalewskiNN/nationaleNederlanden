package com.hz.converter.converter.exchange;

import com.hz.converter.converter.client.AccountClient;
import com.hz.converter.converter.client.NbpExchangeClient;
import com.hz.converter.converter.client.exception.UnsufficientFoundsExeption;
import com.hz.converter.converter.client.response.account.Account;
import com.hz.converter.converter.client.response.account.Balance;
import com.hz.converter.converter.client.response.nbp.Rate;
import com.hz.converter.converter.controller.request.ExchangeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Component
public class Exchange {

    @Autowired
    private NbpExchangeClient nbpExchangeClient;
    @Autowired
    private AccountClient accountClient;

    public void exchangeCurrency(String appId, BigDecimal ammount, ExchangeEnum exchangeEnum) {
        Account account = accountClient.getAccount(appId);
        if (exchangeEnum.equals(ExchangeEnum.PLN2USD) && checkBalance(account.getBalancePln(), ammount)) {
            log.info("Exchanging PLN to USD");
            BigDecimal usdAmmount = exchange(ammount, ExchangeEnum.PLN2USD);
            log.info("Exchanged " + ammount + " PLN to " + usdAmmount + " USD");
            accountClient.updateBalance(new Balance(account.getAppId(), account.getBalancePln().subtract(ammount), account.getBalanceUsd().add(usdAmmount)));
        } else if (exchangeEnum.equals(ExchangeEnum.USD2PLN) && checkBalance(account.getBalanceUsd(), ammount)) {
            log.info("Exchanging USD to PLN");
            BigDecimal plnAmmount = exchange(ammount, ExchangeEnum.USD2PLN);
            log.info("Exchanged " + ammount + " USD to " + plnAmmount + " PLN");
            accountClient.updateBalance(new Balance(account.getAppId(), account.getBalanceUsd().subtract(ammount), account.getBalancePln().add(plnAmmount)));
        } else {
            log.error("Account does not have enough founds");
            throw new UnsufficientFoundsExeption(appId + " does not have enough founds");
        }
    }
    private BigDecimal exchange(BigDecimal ammount, ExchangeEnum exchangeEnum) {
        Rate rate = nbpExchangeClient.getExchangeRateUSD();
        if (exchangeEnum.equals(ExchangeEnum.USD2PLN)) {
            return ammount.multiply(BigDecimal.valueOf(rate.getBid())).setScale(2, RoundingMode.HALF_EVEN);
        } else {
            return ammount.divide(BigDecimal.valueOf(rate.getAsk()), 2, RoundingMode.HALF_EVEN);
        }
    }
    private boolean checkBalance(BigDecimal balance, BigDecimal ammount) {
        return balance.compareTo(ammount) > -1;
    }

}
