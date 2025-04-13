package com.acko.interfaces;

import com.acko.models.Transaction;

import java.util.List;

public interface StatementAdapter {
    List<Transaction> getTransactions();
}
