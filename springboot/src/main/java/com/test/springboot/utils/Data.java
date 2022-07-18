package com.test.springboot.utils;

import com.test.springboot.models.Account;
import com.test.springboot.models.Bank;
import com.test.springboot.models.dto.TransactionRequestDto;

public class Data {

    public static final Account account1 = new Account(1L, "Eric", 200.0);
    public static final Account account2 = new Account(2L, "Eric", 500.0);
    public static final Account accountToCreate = new Account(null, "Test", 600.);

    public static final Bank bank1 = new Bank(1L, "HubBank", 0);

    public static final TransactionRequestDto transactionRequest = new TransactionRequestDto(1L, 2L, 10.0, 1L);
}
