package com.acko.models;

import com.acko.enums.Category;
import com.acko.enums.TransactionType;

import java.time.LocalDate;

public class Transaction {
    private final double amount;
    private final TransactionType type;
    private final Category category;
    private final LocalDate date;
    private final String source;

    public Transaction(double amount, TransactionType type, Category category, LocalDate date, String source) {
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.date = date;
        this.source = source;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public Category getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isExpense() {
        return type == TransactionType.DEBIT && category.isExpense();
    }

    public boolean isIncome() {
        return type == TransactionType.CREDIT && category.isIncome();
    }

    public boolean isInvestment() {
        return type == TransactionType.DEBIT && category.isInvestment();
    }

    public String getSource() {
        return source;
    }

    @Override
    public String toString() {
        return String.format("Transaction{amount=%.2f, type=%s, category=%s, date=%s, source='%s'}",
                amount, type, category, date, source);
    }
}
