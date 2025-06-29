package org.SkyPay.BankingSystem.datetime;

import java.time.LocalDate;
import java.util.List;

public class TestDateTimeProvider implements DateTimeProvider {
    private final List<LocalDate> dates;
    private int index = 0;

    public TestDateTimeProvider(List<LocalDate> dates) {
        this.dates = dates;
    }

    @Override
    public LocalDate currentDate() {
        if (index >= dates.size()) {
            throw new IndexOutOfBoundsException("No more dates available");
        }
        return dates.get(index++);
    }
}
