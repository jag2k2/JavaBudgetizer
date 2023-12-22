package com.jag2k2.listeners;

import com.jag2k2.datastores.TransactionStoreAdder;
import com.jag2k2.importers.TransactionImporter;
import com.jag2k2.tuples.Transaction;
import com.jag2k2.util.Transactions;
import java.awt.event.*;

public class UserImportsTransactionsListener implements ActionListener {
    private final TransactionStoreAdder storeAdder;
    private final TransactionImporter transactionImporter;

    public UserImportsTransactionsListener(TransactionStoreAdder storeAdder, TransactionImporter transactionImporter){
        this.storeAdder = storeAdder;
        this.transactionImporter = transactionImporter;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Transactions<Transaction> transactionsToImport = transactionImporter.getTransactionsToImport();
        storeAdder.addTransactions(transactionsToImport);
    }
}
