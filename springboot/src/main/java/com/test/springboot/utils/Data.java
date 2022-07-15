package com.test.springboot.utils;

import com.test.springboot.models.Account;
import com.test.springboot.models.Bank;

public class Data {

    public static final Account account1 = new Account(1L, "Eric", 200.0);
    public static final Account account2 = new Account(2L, "Eric", 500.0);

    public static final Bank bank1 = new Bank(1L, "HubBank", 0);
}
