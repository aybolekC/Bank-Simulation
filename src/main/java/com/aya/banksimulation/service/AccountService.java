package com.aya.banksimulation.service;

import com.aya.banksimulation.entity.Account;
import com.aya.banksimulation.enums.AccountType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface AccountService {

   Account createNewAcount(BigDecimal balance, Date creationDate, AccountType accountType, Long userId);

   List<Account> listAllAccount();
}
