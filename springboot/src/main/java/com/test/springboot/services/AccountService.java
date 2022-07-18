package com.test.springboot.services;

import com.test.springboot.models.Account;
import com.test.springboot.models.Bank;

import java.util.List;

public interface AccountService {

    List<Account> findAll();
    Account findById(Long id);
    Bank findBankById(Long bankId);
    int getTotalTransfers(Long bankId);
    Double getBalance(Long accountId);
    void makeTransfer(Long accountOrigin, Long accountDestiny, Double amount, Long bankId);
    Account save(Account account);
    void deleteById(Long id);

}
