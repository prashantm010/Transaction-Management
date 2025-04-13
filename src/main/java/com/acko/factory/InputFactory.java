package com.acko.factory;


import com.acko.adapters.BankAdapter;
import com.acko.adapters.SplitwiseAdapter;
import com.acko.adapters.WalletAdapter;
import com.acko.exceptions.InvalidTransactionException;
import com.acko.interfaces.StatementAdapter;

import java.util.Map;

public class InputFactory {
    public static StatementAdapter createAdapter(String type, Map<String, Object> statement) {
        return switch (type.toLowerCase()) {
            case "bank" -> new BankAdapter(statement);
            case "wallet", "online wallet" -> new WalletAdapter(statement);
            case "splitwise" -> new SplitwiseAdapter(statement);
            default -> throw new InvalidTransactionException("Unknown statement type: " + type);
        };
    }
}
