package flb.components.editor.transaction;

import flb.util.Maybe;
import flb.util.Transactions;
import flb.tuples.Transaction;

public interface TransactionTableModel {
    Maybe<? extends Transaction> getTransaction(int row);
    Transactions<? extends Transaction> getTransactions();
}
