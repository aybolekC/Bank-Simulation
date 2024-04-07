package com.aya.banksimulation.service;

import com.aya.banksimulation.enums.AccountStatus;
import com.aya.banksimulation.model.Account;
import com.aya.banksimulation.enums.AccountType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface AccountService {

   Account createNewAcount(BigDecimal balance, Date creationDate, AccountType accountType, Long userId, AccountStatus accountStatus);

   List<Account> listAllAccount();

   void deleteAccount(UUID id);

   Account retriveById(UUID accountId);
}
