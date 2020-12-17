package flb.components.editor.transaction;

import flb.util.Maybe;
import flb.util.Transactions;
import flb.tuples.Transaction;

public interface TransactionTableModel<T extends Transaction> {
    Maybe<T> getTransaction(int row);
    Transactions<T> getTransactions();
    void updateTransactions(Transactions<T> tableContents);
}
