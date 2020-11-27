package flb.importers;

import flb.util.Transactions;
import flb.tuples.Transaction;

public interface TransactionImporter {
    Transactions<Transaction> getTransactionsToImport();
}
