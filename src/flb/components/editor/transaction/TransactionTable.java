package flb.components.editor.transaction;

import flb.tuples.Transaction;
import flb.util.Transactions;

public interface TransactionTable<T extends Transaction> {
    T getTransaction(int row);
    void display(Transactions<T> tableContents);
}
