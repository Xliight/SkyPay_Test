package org.SkyPay.BankingSystem;

import org.SkyPay.BankingSystem.datetime.DateTimeProvider;
import org.SkyPay.BankingSystem.datetime.TestDateTimeProvider;
import org.SkyPay.BankingSystem.service.Account;
import org.SkyPay.BankingSystem.service.AccountService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<LocalDate> dates = Arrays.asList(
                LocalDate.of(2012, 1, 10),
                LocalDate.of(2012, 1, 13),
                LocalDate.of(2012, 1, 14)
        );

        DateTimeProvider dateTimeProvider = new TestDateTimeProvider(dates);

        AccountService account = new Account(dateTimeProvider);

        account.deposit(1000);
        account.deposit(2000);
        account.withdraw(500);

        account.printStatement();
    }
}