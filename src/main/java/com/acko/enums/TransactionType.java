package com.acko.enums;

public enum TransactionType {
    CREDIT, DEBIT;

    public static TransactionType from(String type) {
        return switch (type.toLowerCase()) {
            case "credit", "added", "owe" -> CREDIT;
            case "debit", "paid", "owed" -> DEBIT;
            default -> throw new IllegalArgumentException("Invalid transaction type: " + type);
        };
    }
}
