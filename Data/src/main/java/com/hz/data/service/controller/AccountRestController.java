package com.hz.data.service.controller;

import com.hz.data.service.controller.exceptions.AppIdNullException;
import com.hz.data.service.service.AccountService;
import com.hz.data.service.model.Balance;
import com.hz.data.service.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountRestController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/accountDao/account/create")
    public String createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }
    @PutMapping("/accountDao/updateBalance")
    public void updateBalance(@RequestBody Balance balance) {
        accountService.updateBalance(balance.getAppId(), balance.getBalancePln(), balance.getBalanceUsd());
    }

    @GetMapping("/accountDao/account/{appId}")
    public Account getAccount(@PathVariable String appId) {
        if (appId == null || appId.isEmpty()) {
            throw new AppIdNullException("Application ID is empty");
        }
        return accountService.getAccount(appId);
    }
}
