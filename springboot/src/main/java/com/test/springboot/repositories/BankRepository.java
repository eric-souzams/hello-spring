package com.test.springboot.repositories;

import com.test.springboot.models.Bank;

import java.util.List;
import java.util.Optional;

public interface BankRepository {

    List<Bank> findAll();
    Optional<Bank> findById(Long id);
    void update(Bank bank);

}
