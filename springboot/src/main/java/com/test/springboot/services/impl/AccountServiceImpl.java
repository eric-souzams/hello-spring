package com.test.springboot.services.impl;

import com.test.springboot.models.Account;
import com.test.springboot.models.Bank;
import com.test.springboot.repositories.AccountRepository;
import com.test.springboot.repositories.BankRepository;
import com.test.springboot.services.AccountService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private BankRepository bankRepository;

    public AccountServiceImpl(AccountRepository accountRepository, BankRepository bankRepository) {
        this.accountRepository = accountRepository;
        this.bankRepository = bankRepository;
    }

    @Override
    public Account findById(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isEmpty()) {
            throw new RuntimeException("Account not found");
        }

        return account.get();
    }

    @Override
    public Bank findBankById(Long bankId) {
        Optional<Bank> bank = bankRepository.findById(bankId);
        if (bank.isEmpty()) {
            throw new RuntimeException("Bank not found");
        }

        return bank.get();
    }

    @Override
    public int getTotalTransfers(Long bankId) {
        Bank bank = findBankById(bankId);

        return bank.getTotalTransfers();
    }

    @Override
    public Double getBalance(Long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isEmpty()) {
            throw new RuntimeException("Account not found");
        }

        return account.get().getBalance();
    }

    @Override
    public void makeTransfer(Long accountOrigin, Long accountDestiny, Double amount, Long bankId) {
        Bank bank = findBankById(bankId);
        int newTotal = bank.getTotalTransfers() +1;
        bank.setTotalTransfers(newTotal);
        bankRepository.save(bank);

        Account origin = findById(accountOrigin);
        origin.withdraw(amount);
        accountRepository.save(origin);

        Account destiny = findById(accountDestiny);
        destiny.deposit(amount);
        accountRepository.save(destiny);
    }
}
