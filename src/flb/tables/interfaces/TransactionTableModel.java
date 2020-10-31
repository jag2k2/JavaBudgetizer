package flb.tables.interfaces;

import flb.util.*;
import flb.tuples.*;

public interface TransactionTableModel {
    Maybe<Transaction> getTransaction(int row);
}
