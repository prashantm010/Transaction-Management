package com.acko.enums;

public enum Category {
    RENT,
    GROCERY,
    ENTERTAINMENT,
    INVESTMENT,
    SHOPPING,
    SALARY,
    FREELANCE,
    WALLET,
    TRANSFER,
    LOAN_REPAYMENT;

    public boolean isExpense() {
        return switch (this) {
            case RENT, GROCERY, ENTERTAINMENT, SHOPPING, LOAN_REPAYMENT, WALLET -> true;
            case INVESTMENT, TRANSFER, SALARY, FREELANCE -> false;
        };
    }

    public boolean isIncome() {
        return switch (this) {
            case SALARY, FREELANCE -> true;
            default -> false;
        };
    }

    public boolean isInvestment() {
        return this == INVESTMENT;
    }

    public static Category from(String input) {
        try {
            return Category.valueOf(input.toUpperCase().replace(" ", "_"));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid category: " + input);
        }
    }
}
