package com.hz.rest.api.api.controller;

import com.hz.rest.api.api.client.request.Account;
import com.hz.rest.api.api.client.request.ExchangeRequest;
import com.hz.rest.api.api.controller.exception.NoAppIdException;
import com.hz.rest.api.api.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class ApiController {

    @Autowired
    private ApiService apiService;


    @PostMapping("/create")
    public String createAccount(@RequestBody Account account) {
        return apiService.createAccount(account);
    }

    @PutMapping("/account/convert")
    public void createAccount(@RequestBody ExchangeRequest exchangeRequest) {
        if (exchangeRequest == null || exchangeRequest.getAppId() == null || exchangeRequest.getAppId().isEmpty()) {
            throw new NoAppIdException("App id is empty");
        }
        apiService.exchange(exchangeRequest);
    }

    @GetMapping("/account/{appId}")
    public Account getAccount(@PathVariable String appId) {
        if (appId == null || appId.isEmpty()) {
            throw new NoAppIdException("App id is empty");
        }
        return apiService.getAccount(appId);

    }
}
