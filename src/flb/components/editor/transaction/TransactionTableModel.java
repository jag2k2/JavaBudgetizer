package flb.components.editor.transaction;

import flb.util.*;
import flb.tuples.*;
import java.util.ArrayList;

public interface TransactionTableModel {
    Maybe<? extends Transaction> getTransaction(int row);
    ArrayList<? extends Transaction> getTransactions();
}
