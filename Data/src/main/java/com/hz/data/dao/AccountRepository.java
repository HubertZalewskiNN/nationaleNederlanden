package com.hz.data.dao;

import com.hz.data.model.AccountModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface AccountRepository extends CrudRepository<AccountModel, Integer> {
    List<AccountModel> findByApplicationId(String applicationId);
    @Modifying
    @Transactional
    @Query(value = "update ACCOUNTS a set a.BALANCE_PLN = :balancePln and a.BALANCE_USD = :balanceUsd where a.APPLICATION_ID = :appId", nativeQuery = true)
    void updateBalance(@Param(value = "appId") String appId, @Param(value = "balancePln") BigDecimal balancePln, @Param(value = "balanceUsd") BigDecimal balanceUsd);

}
