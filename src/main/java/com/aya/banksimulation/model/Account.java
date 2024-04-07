package com.aya.banksimulation.model;

import com.aya.banksimulation.enums.AccountStatus;
import com.aya.banksimulation.enums.AccountType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;


@Data
@Builder
public class Account {


    private UUID id;

    @NotNull
    @Positive
    private BigDecimal balance;

    @NotNull
    private AccountType accountType;
    private AccountStatus accountStatus;
    private Date creationDate;

    @NotNull
    private Long userId;



}
