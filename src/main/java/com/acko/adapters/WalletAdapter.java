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

public class WalletAdapter implements StatementAdapter {
    private final String source;
    private final List<Map<String, String>> transactions;

    public WalletAdapter(Map<String, Object> walletStatement) {
        this.source = (String) walletStatement.get("provider");
        this.transactions = (List<Map<String, String>>) walletStatement.get("transactions");
    }

    @Override
    public List<Transaction> getTransactions() {
        List<Transaction> result = new ArrayList<>();
        for (Map<String, String> t : transactions) {
            try {
                double amount = Double.parseDouble(t.get("transaction_amount"));
                TransactionType type = TransactionType.from(t.get("type"));
                Category category = Category.WALLET;
                LocalDate date = LocalDate.parse(t.get("payment_date"));

                result.add(new Transaction(amount, type, category, date, source));
            } catch (Exception e) {
                throw new InvalidTransactionException("Error parsing wallet transaction: " + e.getMessage());
            }
        }
        return result;
    }
}
