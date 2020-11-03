package flb.components.editors.tables.models;

import flb.util.*;
import flb.tuples.*;

public interface TransactionTableModel {
    Maybe<Transaction> getTransaction(int row);
}
