package com.acko.services;


import com.acko.enums.Category;
import com.acko.enums.TransactionType;
import com.acko.interfaces.StatementAdapter;
import com.acko.models.Transaction;

import java.time.LocalDate;
import java.util.*;

public class TransactionManager {
    private final TreeMap<LocalDate, List<Transaction>> dateToTransactions = new TreeMap<>();

    public void addStatement(StatementAdapter adapter) {
        for (Transaction txn : adapter.getTransactions()) {
            dateToTransactions.computeIfAbsent(txn.getDate(), k -> new ArrayList<>()).add(txn);
        }
    }

    public void generateReport(LocalDate start, LocalDate end) {
        double openingBalance = 0, closingBalance = 0, income = 0, expenses = 0, investment = 0;
        Map<Category, Double> categoryTotals = new EnumMap<>(Category.class);

        // ✅ Transactions before the period (for opening balance)
        for (Map.Entry<LocalDate, List<Transaction>> entry : dateToTransactions.headMap(start, false).entrySet()) {
            for (Transaction t : entry.getValue()) {
                openingBalance += t.getType() == TransactionType.CREDIT ? t.getAmount() : -t.getAmount();
            }
        }

        // ✅ Transactions in the query range
        for (Map.Entry<LocalDate, List<Transaction>> entry : dateToTransactions.subMap(start, true, end, true).entrySet()) {
            for (Transaction t : entry.getValue()) {
                closingBalance += t.getType() == TransactionType.CREDIT ? t.getAmount() : -t.getAmount();

                if (t.isIncome()) income += t.getAmount();
                if (t.isInvestment()) investment += t.getAmount();
                if (t.isExpense()) {
                    expenses += t.getAmount();
                    categoryTotals.merge(t.getCategory(), t.getAmount(), Double::sum);
                }
            }
        }

        // 🔍 Report Output
        System.out.println("------ Transaction Report ------");
        System.out.println("Start Date: " + start + " | End Date: " + end);
        System.out.println("Opening Balance: ₹" + openingBalance);
        System.out.println("Closing Balance: ₹" + (openingBalance + closingBalance));
        System.out.println("Income Accrued: ₹" + income);
        System.out.println("Expenses Incurred: ₹" + expenses);
        System.out.println("Investments: ₹" + investment);
        System.out.println("Savings: ₹" + (income - expenses - investment));

        // 🧾 Grouped by Category
        if (!categoryTotals.isEmpty()) {
            System.out.println("\nExpenses by Category:");
            for (Map.Entry<Category, Double> entry : categoryTotals.entrySet()) {
                System.out.printf(" - %s: ₹%.2f%n", entry.getKey(), entry.getValue());
            }
        }
    }
}
