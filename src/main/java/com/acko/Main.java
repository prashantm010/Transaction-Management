package com.acko;

import com.acko.factory.InputFactory;
import com.acko.services.TransactionManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        TransactionManager manager = new TransactionManager();

        Map<String, Object> sbiStatement = Map.of(
                "bank", "SBI",
                "transactions", List.of(
                        Map.of("amount", "5000", "type", "debit", "category", "rent", "timestamp", "2025-03-01"),
                        Map.of("amount", "10000", "type", "credit", "category", "salary", "timestamp", "2025-03-05")
                )
        );

        Map<String, Object> hdfcStatement = Map.of(
                "bank", "HDFC",
                "transactions", List.of(
                        Map.of("amount", "2500", "type", "debit", "category", "entertainment", "timestamp", "2025-03-10"),
                        Map.of("amount", "2150", "type", "debit", "category", "investment", "timestamp", "2025-03-05")
                )
        );

        Map<String, Object> walletStatement = Map.of("provider", "paytm", "transactions", List.of(Map.of("transaction_amount", "200", "type", "paid", "payment_date", "2025-03-03")));

        Map<String, Object> splitwiseStatement = Map.of("transactions", List.of(Map.of("amount", "150", "description", "Grocery", "added_by", "you", "type", "owed", "transaction_date", "2025-03-02")));


        manager.addStatement(InputFactory.createAdapter("bank", sbiStatement));
        manager.addStatement(InputFactory.createAdapter("bank", hdfcStatement));
        manager.addStatement(InputFactory.createAdapter("wallet", walletStatement));
        manager.addStatement(InputFactory.createAdapter("splitwise", splitwiseStatement));

        manager.generateReport(LocalDate.of(2025, 3, 1), LocalDate.of(2025, 3, 31));
    }
}
