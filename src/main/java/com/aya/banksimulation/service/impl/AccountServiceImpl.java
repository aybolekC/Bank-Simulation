package com.aya.banksimulation.service.impl;

import com.aya.banksimulation.enums.AccountStatus;
import com.aya.banksimulation.model.Account;
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
    public Account createNewAcount(BigDecimal balance, Date creationDate, AccountType accountType, Long userId, AccountStatus accountStatus) {
        Account account=Account.builder().id(UUID.randomUUID()).userId(userId).accountType(accountType).accountStatus(AccountStatus.ACTIVE)
                .balance(balance).creationDate(creationDate).build();

        return accountRepository.save(account);
    }

    @Override
    public List<Account> listAllAccount() {
        return accountRepository.findAll();
    }

    @Override
    public void deleteAccount(UUID id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Account retriveById(UUID accountId) {
        return accountRepository.findById(accountId);
    }


}
