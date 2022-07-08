package com.test.junit5.models;

import com.test.junit5.exceptions.NotEnoughMoneyException;

import java.util.Objects;

public class Account {

    private String name;
    private Double amount;
    private Bank bank;

    public Account() {}

    public Account(String name) {
        this.name = name;
    }

    public Account(Double amount) {
        this.amount = amount;
    }

    public Account(String name, Double amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public void withdraw(Double amount) {
        if (!(this.amount >= amount)) {
            throw new NotEnoughMoneyException("Account have not enough money to withdraw.");
        }

        this.amount -= amount;
    }

    public void deposit(Double amount) {
        this.amount += amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(name, account.name) && Objects.equals(amount, account.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, amount);
    }
}
