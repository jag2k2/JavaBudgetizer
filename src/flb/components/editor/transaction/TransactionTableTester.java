package flb.components.editor.transaction;

import flb.util.Transactions;
import flb.tuples.Transaction;

public interface TransactionTableTester {
    Transactions<? extends Transaction> getTransactions();
}
