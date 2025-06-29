package org.SkyPay.BankingSystem.output;



import org.SkyPay.BankingSystem.domaine.Transaction;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

public class StatementFormatter {
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String HEADER = "Date | Amount | Balance";

    public String format(List<Transaction> transactions) {
        StringJoiner sj = new StringJoiner("\n");
        sj.add(HEADER);

        List<Transaction> reversed = new ArrayList<>(transactions);
        Collections.reverse(reversed);

        reversed.forEach(t -> sj.add(
                t.date().format(DATE_FORMAT) + " | " +
                        t.amount() + " | " +
                        t.balance()
        ));

        return sj.toString();
    }
}