package com.test.springboot.services;

import com.test.springboot.models.Account;
import com.test.springboot.models.Bank;

public interface AccountService {

    Account findById(Long id);
    Bank findBankById(Long bankId);
    int getTotalTransfers(Long bankId);
    Double getBalance(Long accountId);
    void makeTransfer(Long accountOrigin, Long accountDestiny, Double amount, Long bankId);

}
