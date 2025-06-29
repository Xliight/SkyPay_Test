package org.SkyPay.BankingSystem.application;

import org.SkyPay.BankingSystem.datetime.DateTimeProvider;
import org.SkyPay.BankingSystem.exception.InsufficientFundsException;
import org.SkyPay.BankingSystem.service.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
        private Account account;
        private MockDateTimeProvider dateTimeProvider;

        @BeforeEach
        void setup() {
            dateTimeProvider = new MockDateTimeProvider();
            account = new Account(dateTimeProvider);
        }

        @Test
        void deposit_should_add_transaction_and_increase_balance() {
            dateTimeProvider.setFixedDate(LocalDate.of(2022, 1, 1));
            account.deposit(1000);

            assertEquals(1, account.transactions.size());
            assertEquals(1000, account.transactions.get(0).balance());
        }

        @Test
        void withdraw_should_add_negative_transaction_and_decrease_balance() {
            dateTimeProvider.setFixedDate(LocalDate.of(2022, 1, 2));
            account.deposit(2000);
            account.withdraw(500);

            assertEquals(2, account.transactions.size());
            assertEquals(1500, account.transactions.get(1).balance());
            assertEquals(-500, account.transactions.get(1).amount());
        }

        @Test
        void withdraw_more_than_balance_should_throw_exception() {
            dateTimeProvider.setFixedDate(LocalDate.of(2022, 1, 3));
            account.deposit(1000);

            InsufficientFundsException exception = assertThrows(
                    InsufficientFundsException.class,
                    () -> account.withdraw(1500)
            );

            assertTrue(exception.getMessage().contains("Cannot withdraw"));
        }

        @Test
        void deposit_negative_amount_should_throw_exception() {
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> account.deposit(-100)
            );

            assertEquals("Deposit amount must be positive: -100", exception.getMessage());
        }

        @Test
        void acceptance_test_should_print_correct_statement() {

            dateTimeProvider.setFixedDate(LocalDate.of(2012, 1, 10));
            account.deposit(1000);

            dateTimeProvider.setFixedDate(LocalDate.of(2012, 1, 13));
            account.deposit(2000);

            dateTimeProvider.setFixedDate(LocalDate.of(2012, 1, 14));
            account.withdraw(500);

            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            account.printStatement();

            String expected =
                    "Date | Amount | Balance\n" +
                            "14/01/2012 | -500 | 2500\n" +
                            "13/01/2012 | 2000 | 3000\n" +
                            "10/01/2012 | 1000 | 1000";

            String actual = outContent.toString().trim().replace("\r\n", "\n");

            assertEquals(expected, actual);
        }

        static class MockDateTimeProvider implements DateTimeProvider {
            private LocalDate fixedDate;

            public void setFixedDate(LocalDate date) {
                this.fixedDate = date;
            }

            @Override
            public LocalDate currentDate() {
                return fixedDate;
            }
        }
}