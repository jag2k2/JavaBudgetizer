package com.jag2k2.datastores;

import com.jag2k2.tuples.Transaction;
import com.jag2k2.util.Transactions;

public interface TransactionStoreAdder extends StoreChangeNotifier {
    void addTransactions(Transactions<? extends Transaction> transactions);
}
