package com.acko.adapters;

import com.acko.enums.Category;
import com.acko.enums.TransactionType;
import com.acko.exceptions.InvalidTransactionException;
import com.acko.interfaces.StatementAdapter;
import com.acko.models.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BankAdapter implements StatementAdapter {
    private final String source;
    private final List<Map<String, String>> transactions;

    public BankAdapter(Map<String, Object> banksStatement) {
        this.source = (String) banksStatement.get("bank");
        this.transactions = (List<Map<String, String>>) banksStatement.get("transactions");
    }

    @Override
    public List<Transaction> getTransactions() {
        List<Transaction> result = new ArrayList<>();
        for (Map<String, String> t : transactions) {
            try {
                double amount = Double.parseDouble(t.get("amount"));
                TransactionType type = TransactionType.from(t.get("type"));
                Category category = Category.from(t.get("category"));
                LocalDate date = LocalDate.parse(t.get("timestamp"));
                result.add(new Transaction(amount, type, category, date, source));
            } catch (Exception e) {
                throw new InvalidTransactionException("Error in bank transaction for " + source + ": " + e.getMessage());
            }
        }
        return result;
    }
}
