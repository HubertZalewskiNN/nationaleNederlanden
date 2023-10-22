package com.hz.data.service.service;

import com.hz.data.dao.AccountRepository;
import com.hz.data.model.AccountModel;
import com.hz.data.service.controller.exceptions.AccountNotFoundExeption;
import com.hz.data.service.controller.exceptions.MissingBalanceException;
import com.hz.data.service.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class AccountService {

    @Autowired
    private AccountRepository repository;

    public String createAccount(Account account) {
        String appId = UUID.randomUUID().toString();
        AccountModel accountModel = AccountModel.builder()
                .applicationId(appId)
                .name(account.getName())
                .lastName(account.getLastName())
                .balancePln(account.getBalancePln())
                .balanceUsd(BigDecimal.ZERO)
                .build();
        repository.save(accountModel);
        log.info("New account has been created with application ID = " + appId);
        return appId;
    }

    public Account getAccount(String appId) throws AccountNotFoundExeption {
        AccountModel accountModel = getSingleAccount(appId);
        log.info("Returned account " + accountModel.getApplicationId());
        return Account.builder()
                .appId(accountModel.getApplicationId())
                .name(accountModel.getName())
                .lastName(accountModel.getLastName())
                .balancePln(accountModel.getBalancePln())
                .balanceUsd(accountModel.getBalanceUsd())
                .build();
    }

    public void updateBalance(String appId, BigDecimal balancePln, BigDecimal balanceUsd) throws AccountNotFoundExeption {
        if (balancePln == null || balanceUsd == null) {
            throw new MissingBalanceException("PLN or USD ammount are null");
        }
        repository.updateBalance(appId, balancePln, balanceUsd);
        log.info("Balance of account with application ID = " + appId + " has been updated");
    }


    private AccountModel getSingleAccount(String appId) throws AccountNotFoundExeption {
        List<AccountModel> accountModelList = repository.findByApplicationId(appId);
        if (accountModelList == null || accountModelList.size() != 1) {
            log.error("Account appId = " + appId + " does not exists");
            throw new AccountNotFoundExeption("Account appId = " + appId + " does not exists or are more then one");
        }
        return accountModelList.get(0);
    }
}
