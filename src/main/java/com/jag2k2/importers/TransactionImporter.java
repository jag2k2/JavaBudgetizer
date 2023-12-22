package com.jag2k2.importers;

import com.jag2k2.util.Transactions;
import com.jag2k2.tuples.Transaction;

public interface TransactionImporter {
    Transactions<Transaction> getTransactionsToImport();
}
