package flb.tables.interfaces;

import flb.components.*;
import flb.tuples.*;
import flb.util.*;

public interface TransactionCategorizer {
    void addCategorizingListener(CategoryMenuImpl categoryMenuImpl);
    Maybe<Transaction> getTransaction(int row);
}
