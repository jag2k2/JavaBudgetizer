package flb.listeners;

import flb.datastores.TransactionStore;
import flb.importers.TransactionImporter;
import flb.tuples.Transaction;
import java.util.List;

import java.awt.event.*;

public class UserImportsTransactionsListener implements ActionListener {
    private final TransactionStore transactionStore;
    private final TransactionImporter transactionImporter;

    public UserImportsTransactionsListener(TransactionStore transactionStore, TransactionImporter transactionImporter){
        this.transactionStore = transactionStore;
        this.transactionImporter = transactionImporter;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<Transaction> transactionsToImport = transactionImporter.getTransactionsToImport();
        transactionStore.addTransactions(transactionsToImport);
    }
}
