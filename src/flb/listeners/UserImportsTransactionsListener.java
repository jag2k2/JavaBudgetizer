package flb.listeners;

import flb.datastores.TransactionStoreAdder;
import flb.importers.TransactionImporter;
import flb.tuples.Transaction;
import flb.util.Transactions;
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
