package com.hz.rest.api.api.client;

import com.hz.rest.api.api.client.exception.AccountCreationException;
import com.hz.rest.api.api.client.handler.AccountErrorHandler;
import com.hz.rest.api.api.client.request.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class AccountClient {

    @Autowired
    private RestTemplate restTemplate;

    public Account getAccount(String appId) {
        restTemplate.setErrorHandler(new AccountErrorHandler());
        return restTemplate.getForObject("http://localhost:8080/hz4nndao/accountDao/account/{appId}", Account.class, appId);
    }
    public String createAccount(Account account) {
        restTemplate.setErrorHandler(new AccountErrorHandler());
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/accountDao/account/create", account, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        throw new AccountCreationException("Something went wrong. No Appliaction ID");
    }
}
