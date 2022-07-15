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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

	@Test
	void contextLoads3() {
		when(accountRepository.findById(anyLong())).thenReturn(Optional.empty());
		assertThrows(RuntimeException.class, () -> accountService.findById(8L));

		when(bankRepository.findById(anyLong())).thenReturn(Optional.empty());
		assertThrows(RuntimeException.class, () -> accountService.findBankById(8L));

		when(accountRepository.findById(anyLong())).thenReturn(Optional.empty());
		assertThrows(RuntimeException.class, () -> accountService.getBalance(8L));

		when(bankRepository.findById(anyLong())).thenReturn(Optional.of(Data.bank1));
		int total = accountService.getTotalTransfers(1L);
		assertEquals(1, total);
		verify(bankRepository, times(2)).findById(anyLong());
	}

	@Test
	void testFindAll() {
		//given
		List<Account> data = List.of(Data.account1, Data.account2);
		when(accountRepository.findAll()).thenReturn(data);

		//when
		List<Account> accounts = accountService.findAll();

		//then
		assertFalse(accounts.isEmpty());
		assertEquals(2, accounts.size());
		verify(accountRepository).findAll();
	}

	@Test
	void testSave() {
		//given
		Account account = new Account(null, "Polo", 10.0);

		when(accountRepository.save(any(Account.class))).then(invocationOnMock -> {
			Account ac = invocationOnMock.getArgument(0);
			ac.setId(5L);
			return ac;
		});

		//when
		Account savedAccount = accountService.save(account);

		//then
		assertEquals("Polo", savedAccount.getName());
		assertEquals(5, savedAccount.getId());
		verify(accountRepository).save(any(Account.class));
	}
}
