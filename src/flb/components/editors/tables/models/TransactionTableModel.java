package flb.components.editors.tables.models;

import flb.util.*;
import flb.tuples.*;
import java.util.ArrayList;

public interface TransactionTableModel<E> {
    Maybe<Transaction> getTransaction(int row);
    ArrayList<E> getTransactions();
}
