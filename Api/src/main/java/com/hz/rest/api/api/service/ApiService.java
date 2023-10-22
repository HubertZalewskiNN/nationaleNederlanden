package com.hz.rest.api.api.service;

import com.hz.rest.api.api.client.AccountClient;
import com.hz.rest.api.api.client.ConverterClient;
import com.hz.rest.api.api.client.request.Account;
import com.hz.rest.api.api.client.request.ExchangeRequest;
import com.hz.rest.api.api.controller.exception.NoAccountException;
import com.hz.rest.api.api.controller.exception.NoAppIdException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ApiService {

    @Autowired
    private AccountClient accountClient;
    @Autowired
    private ConverterClient converterClient;

    public String createAccount(Account account) {
        if (account == null) {
            throw new NoAccountException("No account to create");
        } else if (account.getName() == null || account.getName().isEmpty() ||
                account.getLastName() == null || account.getLastName().isEmpty() ||
                account.getBalancePln() == null) {
            throw new NoAccountException("Name, last name and balance in PLN is mandatory");
        }
        log.info("Sending message to create new account");
        return accountClient.createAccount(account);
    }

    public Account getAccount(String appId) {
        if (appId == null || appId.isEmpty()) {
            throw new NoAppIdException("Application ID not found");
        }
        Account account = accountClient.getAccount(appId);
        if (account != null && account.getAppId()!= null && !account.getAppId().isEmpty()) {
            return account;
        }
        throw new NoAccountException("Account was not found");
    }

    public void exchange(ExchangeRequest exchangeRequest) {
        if (exchangeRequest.getAppId() == null || exchangeRequest.getAppId().isEmpty()) {
            throw new NoAppIdException("Application ID not found");
        }
        converterClient.exchange(exchangeRequest);
    }
}
