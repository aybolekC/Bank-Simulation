package com.aya.banksimulation.service.impl;

import com.aya.banksimulation.entity.Account;
import com.aya.banksimulation.enums.AccountType;
import com.aya.banksimulation.repository.AccountRepository;
import com.aya.banksimulation.service.AccountService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Component
public class AccountServiceImpl implements AccountService {


    AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account createNewAcount(BigDecimal balance, Date creationDate, AccountType accountType, Long userId) {
        Account account=Account.builder().id(UUID.randomUUID()).userId(userId).accountType(accountType)
                .balance(balance).creationDate(creationDate).build();

        return accountRepository.save(account);
    }

    @Override
    public List<Account> listAllAccount() {
        return accountRepository.findAll();
    }
}
