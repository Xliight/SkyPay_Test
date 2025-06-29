package org.SkyPay.BankingSystem.datetime;

import java.time.LocalDate;

@FunctionalInterface
public interface DateTimeProvider {
    LocalDate currentDate();
}
