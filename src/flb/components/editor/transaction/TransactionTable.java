package flb.components.editor.transaction;

import flb.util.Maybe;
import flb.tuples.Transaction;
import flb.util.Transactions;

public interface TransactionTable<T extends Transaction> {
    Maybe<T> getTransaction(int row);
    void display(Transactions<T> tableContents);
}
