package org.SkyPay.BankingSystem.service;

import org.SkyPay.BankingSystem.datetime.DateTimeProvider;
import org.SkyPay.BankingSystem.domaine.Transaction;
import org.SkyPay.BankingSystem.exception.InsufficientFundsException;
import org.SkyPay.BankingSystem.output.StatementFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Account implements AccountService {
    private final DateTimeProvider dateTimeProvider;
    public final List<Transaction> transactions = Collections.synchronizedList(new ArrayList<>());
    private int balance = 0;

    public Account(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    public Account() {
        this(() -> java.time.LocalDate.now());
    }

    @Override
    public void deposit(int amount) {
        validatePositiveAmount(amount, "Deposit");
        balance += amount;
        transactions.add(new Transaction(
                dateTimeProvider.currentDate(),
                amount,
                balance
        ));
    }

    @Override
    public void withdraw(int amount) {
        validatePositiveAmount(amount, "Withdrawal");
        if (amount > balance) {
            throw new InsufficientFundsException(
                    "Cannot withdraw " + amount + " - current balance: " + balance
            );
        }
        balance -= amount;
        transactions.add(new Transaction(
                dateTimeProvider.currentDate(),
                -amount,
                balance
        ));
    }

    @Override
    public void printStatement() {
        StatementFormatter formatter = new StatementFormatter();
        System.out.println(formatter.format(transactions));
    }

    private void validatePositiveAmount(int amount, String operation) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                    operation + " amount must be positive: " + amount
            );
        }
    }
}