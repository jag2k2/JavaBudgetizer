package com.jag2k2.components.editor.transaction;

import com.jag2k2.util.Transactions;
import com.jag2k2.tuples.Transaction;

public interface TransactionTableModel<T extends Transaction> {
    T getTransaction(int row);
    Transactions<T> getTransactions();
    void updateTransactions(Transactions<T> tableContents);
}
