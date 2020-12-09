package flb.datastores;

import flb.tuples.Transaction;
import flb.util.Transactions;

public interface TransactionStoreAdder extends StoreChangeNotifier {
    void addTransactions(Transactions<? extends Transaction> transactions);
}
