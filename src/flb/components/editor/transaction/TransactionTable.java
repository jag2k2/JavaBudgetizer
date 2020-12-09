package flb.components.editor.transaction;

import flb.util.Maybe;
import flb.tuples.Transaction;

public interface TransactionTable {
    Maybe<? extends Transaction> getTransaction(int row);
}
