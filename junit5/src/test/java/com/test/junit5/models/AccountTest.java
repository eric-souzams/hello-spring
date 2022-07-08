package com.test.junit5.models;

import com.test.junit5.exceptions.NotEnoughMoneyException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    private Account account;

    @BeforeEach
    void setUp() {
        this.account = new Account("Eric", 245.8);
    }

    @Tag("account")
    @Nested
    class AccountNestedTest {
        @Test
        @DisplayName("Testing if account name match")
        public void testAccountName() {
            assertNotNull(account);
            assertEquals("Eric", account.getName());
        }

        @Test
        @Disabled //Will ignore test when run all tests
        @DisplayName("Testing if account name match 2")
        public void testAccountName2() {
            assertNotNull(account);
            assertEquals("Eric", account.getName());
        }

        @Test
        @DisplayName("Testing if account amount match")
        void testAccountAmount() {
            assertNotNull(account);
            assertEquals(245.8, account.getAmount());
            assertTrue(account.getAmount().compareTo(0.0) > 0);
        }

        @DisplayName("Testing if account can make withdraw")
        @RepeatedTest(value = 6, name = "{displayName} - Repetition {currentRepetition} of {totalRepetitions}")
        void testAccountWithdraw(RepetitionInfo info) {
            System.out.println(info.getCurrentRepetition());

            account.withdraw(45.8);

            assertNotNull(account);
            assertEquals(200.0, account.getAmount());
        }

        @Test
        @DisplayName("Testing if account can not make deposit")
        void testAccountNotHaveEnoughMoneyToWithdraw() {
            NotEnoughMoneyException exception = assertThrows(NotEnoughMoneyException.class, () -> {
                account.withdraw(500.0);
            });

            assertEquals("Account have not enough money to withdraw.", exception.getMessage());
        }
    }

    @Nested
    class AccountNestedParameterizedTest {
        @Tag("account")
        @ParameterizedTest(name = "number {index} running with value {0} - {argumentsWithNames}")
        @ValueSource(doubles = {10.0, 150.0, 326.0, 489.60})
        @DisplayName("Testing if account can make deposit")
        void testAccountDeposit(Double amount) {
            Double expectedAmount = account.getAmount() + amount;
            account.deposit(amount);

            assertNotNull(account);
            assertEquals(expectedAmount, account.getAmount());
        }

        @Tag("account")
        @DisplayName("Testing if account can make deposit 2")
        @ParameterizedTest(name = "number {index} running with value {0} - {argumentsWithNames}")
        @MethodSource(value = "com.test.junit5.models.AccountTest#amountList")
        void testAccountDeposit2(Double amount) {
            Double expectedAmount = account.getAmount() + amount;
            account.deposit(amount);

            assertNotNull(account);
            assertEquals(expectedAmount, account.getAmount());
        }
    }

    static List<Double> amountList() {
        return Arrays.asList(10.0, 150.0, 326.0, 489.60);
    }

    @Tag("bank")
    @Nested
    class AccountBankNestedTest {
        @Test
        @DisplayName("Testing if a bank can make transfer")
        void testTransferMoneyBetweenAccounts() {
            Account account1 = new Account("Eric", 150.0);
            Account account2 = new Account("Maria", 250.0);
            Bank bank = new Bank("Bank of GitHub");

            bank.transfer(account2, account1, 50.0);

            assertAll(
                    () -> assertEquals(200.00, account1.getAmount()),
                    () -> assertEquals(200.0, account2.getAmount())
            );
        }

        @Test
        @DisplayName("Testing if account can make relation with a bank")
        void testRelationWithBank() {
            Account account1 = new Account("Eric", 150.0);
            Account account2 = new Account("Maria", 250.0);

            Bank bank = new Bank("Bank of GitHub");
            bank.addAccount(account1);
            bank.addAccount(account2);

            assertEquals(2, bank.getAccounts().size());
            assertEquals(bank.getName(), account1.getBank().getName());
            assertEquals("Eric", bank.getAccounts().stream().filter(a -> a.getName().equals("Eric")).findFirst().get().getName());
            assertTrue(bank.getAccounts().stream().anyMatch(a -> a.getName().equals("Eric")));
        }
    }

}