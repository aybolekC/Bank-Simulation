package com.aya.banksimulation.repository;

import com.aya.banksimulation.enums.AccountStatus;
import com.aya.banksimulation.model.Account;
import com.aya.banksimulation.exception.RecordNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Component
public class AccountRepository {

    public static List<Account> accountList=new ArrayList<>();

    public Account save(Account account){

        accountList.add(account);
        return account;

    }


    public List<Account> findAll() {

        return accountList;
    }

    public Account findById(UUID accountId){

     return accountList.stream().filter(m->m.getId().equals(accountId))
             .findAny().orElseThrow(()->new RecordNotFoundException("This account is not in the database"));

    }


    public void deleteById(UUID id) {

        Account account=findById(id);

        account.setAccountStatus(AccountStatus.DELETED);


    }
}
