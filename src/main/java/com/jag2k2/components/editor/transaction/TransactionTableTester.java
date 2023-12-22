package com.jag2k2.components.editor.transaction;

import com.jag2k2.util.Transactions;
import com.jag2k2.tuples.Transaction;

public interface TransactionTableTester {
    Transactions<? extends Transaction> getTransactions();
}
