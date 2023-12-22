package com.jag2k2.components.editor.transaction;

import com.jag2k2.tuples.Transaction;
import com.jag2k2.util.Transactions;

public interface TransactionTable<T extends Transaction> {
    T getTransaction(int row);
    void display(Transactions<T> tableContents);
}
