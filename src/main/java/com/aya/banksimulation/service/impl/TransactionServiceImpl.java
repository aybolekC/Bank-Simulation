package com.aya.banksimulation.service.impl;

import com.aya.banksimulation.model.Account;
import com.aya.banksimulation.model.Transaction;
import com.aya.banksimulation.enums.AccountType;
import com.aya.banksimulation.exception.AccountOwnerShipException;
import com.aya.banksimulation.exception.BadRequestException;
import com.aya.banksimulation.exception.BalanceNotSufficientException;
import com.aya.banksimulation.exception.UnderConstructionException;
import com.aya.banksimulation.repository.AccountRepository;
import com.aya.banksimulation.repository.TransactionRepository;
import com.aya.banksimulation.service.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Component
public class TransactionServiceImpl implements TransactionService {

    @Value("${under_construction}")
    private boolean underConstruction;


    AccountRepository accountRepository;
    TransactionRepository transactionRepository;

    public TransactionServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction makeTransfer(BigDecimal amount, Date creationDate, Account sender, Account receiver, String message) {
        if(!underConstruction){
        checkAccountOwnerShip(sender,receiver);
        validateAccounts(sender, receiver);
        executeBalanceAndUpdateIfRequired(amount,sender,receiver);

        return transactionRepository.save(Transaction.builder()
                .amount(amount)
                .creationDate(creationDate)
                .sender(sender.getId())
                .receiver(receiver.getId())
                .message(message).build());
    }
    else {
        throw new UnderConstructionException("Make transfer is not possible right now. Please try again later.");
        }

    }

    private void executeBalanceAndUpdateIfRequired(BigDecimal amount, Account sender, Account receiver) {
        if(checkSenderBalance(sender, amount)){
            sender.setBalance(sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));
        }
        else {
            throw new BalanceNotSufficientException("Balance is not enough for this transaction");
        }

    }

    private boolean checkSenderBalance(Account sender, BigDecimal amount) {

        return sender.getBalance().subtract(amount).compareTo(BigDecimal.ZERO)>0;
    }

    private void validateAccounts(Account sender, Account receiver) {
        if(sender==null || receiver==null){
            throw new BadRequestException("Sender or receiver can not be null");
        }

        if(sender.getId().equals(receiver.getId())){
            throw new BadRequestException("Sender account needs to be different from receiver account ");
        }

        findAccountById(sender.getId());
        findAccountById(receiver.getId());

    }

    private void findAccountById(UUID accountId) {
        accountRepository.findById(accountId);
    }

    private void checkAccountOwnerShip(Account sender, Account receiver) {

        if((sender.getAccountType().equals(AccountType.SAVINGS)||receiver.getAccountType().equals(AccountType.SAVINGS))
        && sender.getUserId().equals(receiver.getUserId())){
            throw new AccountOwnerShipException("When one of the account type is SAVINGS, sender and receiver has to be same person");

        }
    }

    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> retrieveLastTransaction() {
        return transactionRepository.retrieveLastTransactions();
    }

    @Override
    public List<Transaction> findTransactionListById(UUID transactionId) {
        return transactionRepository.findTransactionListById(transactionId);
    }
}
