package com.test.junit5.models;

import java.util.ArrayList;
import java.util.List;

public class Bank {

    private String name;
    private final List<Account> accounts = new ArrayList<>();

    public Bank() {}

    public Bank(String name) {
        this.name = name;
    }

    public void transfer(Account origin, Account destiny, Double amount) {
        origin.withdraw(amount);
        destiny.deposit(amount);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
        account.setBank(this);
    }
}
