package flb.components.editor.transaction;

import flb.util.Maybe;
import flb.tuples.Transaction;
import javax.swing.*;

public interface TransactionTable {
    JPanel getPanel();
    Maybe<? extends Transaction> getTransaction(int row);
}
