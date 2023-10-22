package com.hz.converter.converter.client;

import com.hz.converter.converter.client.handlers.AccountErrorHandler;
import com.hz.converter.converter.client.response.account.Account;
import com.hz.converter.converter.client.response.account.Balance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountClient {

    @Autowired
    private RestTemplate restTemplate;

    public Account getAccount(String appId) {
        restTemplate.setErrorHandler(new AccountErrorHandler());
        return restTemplate.getForObject("http://localhost:8080/hz4nndao/accountDao/account/{appId}", Account.class, appId);
    }
    public void updateBalance(Balance balance) {
        restTemplate.setErrorHandler(new AccountErrorHandler());
        restTemplate.put("http://localhost:8080/accountDao/updateBalance", balance);
    }
}
