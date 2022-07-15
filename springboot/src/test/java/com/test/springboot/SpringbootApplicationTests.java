package com.test.springboot;

import com.test.springboot.exceptions.NotEnoughMoneyException;
import com.test.springboot.models.Account;
import com.test.springboot.models.Bank;
import com.test.springboot.repositories.AccountRepository;
import com.test.springboot.repositories.BankRepository;
import com.test.springboot.services.AccountService;
import com.test.springboot.utils.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class SpringbootApplicationTests {

	@MockBean
	private AccountRepository accountRepository;

	@MockBean
	private BankRepository bankRepository;

	@Autowired
	private AccountService accountService;

	@Test
	void contextLoads() {
		when(accountRepository.findById(1L)).thenReturn(Optional.of(Data.account1));
		when(accountRepository.findById(2L)).thenReturn(Optional.of(Data.account2));
		when(bankRepository.findById(1L)).thenReturn(Optional.of(Data.bank1));

		Double balanceOrigin = accountService.getBalance(1L);
		Double balanceDestiny = accountService.getBalance(2L);

		assertEquals(200.0, balanceOrigin);
		assertEquals(500.0, balanceDestiny);

		accountService.makeTransfer(1L, 2L, 50.0, 1L);

		balanceOrigin = accountService.getBalance(1L);
		balanceDestiny = accountService.getBalance(2L);

		assertEquals(150.0, balanceOrigin);
		assertEquals(550.0, balanceDestiny);

		verify(accountRepository, times(3)).findById(1L);
		verify(accountRepository, times(3)).findById(2L);
		verify(accountRepository, times(2)).save(any(Account.class));

		verify(bankRepository, times(1)).findById(1L);
		verify(bankRepository).save(any(Bank.class));
	}

	@Test
	void contextLoads2() {
		when(accountRepository.findById(1L)).thenReturn(Optional.of(Data.account1));
		when(accountRepository.findById(2L)).thenReturn(Optional.of(Data.account2));
		when(bankRepository.findById(1L)).thenReturn(Optional.of(Data.bank1));

		assertThrows(NotEnoughMoneyException.class, () -> {
			accountService.makeTransfer(1L, 2L, 1050.0, 1L);
		});
	}
}
