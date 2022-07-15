package com.test.springboot;

import com.test.springboot.models.Account;
import com.test.springboot.repositories.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class IntegrationJpaTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void testFindById() {
        Optional<Account> account = accountRepository.findById(1L);

        assertTrue(account.isPresent());
        assertEquals("Eric", account.get().getName());
    }

    @Test
    void testThrowExceptionWhenTryFindById() {
        Optional<Account> account = accountRepository.findById(10L);

        assertThrows(NoSuchElementException.class, account::orElseThrow);
        assertTrue(account.isEmpty());
    }

    @Test
    void testFindByName() {
        Optional<Account> account = accountRepository.findByName("Eric");

        assertTrue(account.isPresent());
        assertEquals("Eric", account.get().getName());
    }

    @Test
    void testThrowExceptionWhenTryFindByName() {
        Optional<Account> account = accountRepository.findByName("Palms");

        assertThrows(NoSuchElementException.class, account::orElseThrow);
        assertTrue(account.isEmpty());
    }

    @Test
    void testFindAll() {
        List<Account> accounts = accountRepository.findAll();

        assertFalse(accounts.isEmpty());
        assertEquals(2, accounts.size());
    }

    @Test
    void testSaveNewAccount() {
        Account account = new Account(null, "Test", 50.0);
        account = accountRepository.save(account);

//        Optional<Account> foundedAccount = accountRepository.findByName("Test");

        assertNotNull(account);
        assertEquals(4, account.getId());
    }

    @Test
    void testUpdateExistentAccount() {
        Account account = new Account(null, "Test", 50.0);
        account = accountRepository.save(account);

        assertEquals(50.0, account.getBalance());

        account.setBalance(100.0);
        Account updatedAccount = accountRepository.save(account);

        assertEquals(100.0, updatedAccount.getBalance());
    }

    @Test
    void testDeleteExistentAccount() {
        Account foundedAccount = accountRepository.findById(2L).orElseThrow();

        assertEquals("Lucas", foundedAccount.getName());

        accountRepository.delete(foundedAccount);

        assertThrows(NoSuchElementException.class, () -> accountRepository.findById(2L).orElseThrow());
    }
}
