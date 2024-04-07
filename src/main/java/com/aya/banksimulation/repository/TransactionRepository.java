package com.aya.banksimulation.repository;
import com.aya.banksimulation.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TransactionRepository {

    public static List<Transaction> transactionList=new ArrayList<>();

    public Transaction save(Transaction transaction){

        transactionList.add(transaction);
        return transaction;

    }


    public List<Transaction> findAll() {

        return transactionList;
    }


    public List<Transaction> retrieveLastTransactions() {
        return transactionList.stream().sorted(Comparator.comparing(Transaction::getCreationDate)).limit(10).collect(Collectors.toList());
    }

    public List<Transaction> findTransactionListById(UUID transactionId) {

        return transactionList.stream()
                .filter(transaction -> transaction.getSender().equals(transactionId)||transaction.getReceiver().equals(transactionId))
                .collect(Collectors.toList());
    }
}
