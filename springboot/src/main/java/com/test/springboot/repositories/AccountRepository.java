package com.test.springboot.repositories;

import com.test.springboot.models.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {

    List<Account> findAll();
    Optional<Account> findById(Long id);
    void update(Account account);

}
