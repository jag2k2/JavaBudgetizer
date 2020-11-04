package flb.components.editors;

import flb.components.categoryselector.*;
import flb.datastores.*;
import flb.components.editors.tables.*;
import flb.tuples.*;
import flb.util.*;
import javax.swing.*;
import java.util.*;

public class BankingEditorImpl implements BankingEditorTester, TransactionCategorizer, MonthChangeListener, StoreChangeListener {
    private final TransactionStore transactionStore;
    private final BankingTable bankingTable;
    private final BankingTableTester tableAutomator;
    private final ArrayList<StoreChangeListener> storeChangeListeners;

    public BankingEditorImpl(TransactionStore transactionStore, CategoryStore categoryStore) {
        this.transactionStore = transactionStore;
        CategoryMenuImpl categoryMenu = new CategoryMenuImpl(categoryStore, this);
        BankingTableImpl bankingTableImpl = new BankingTableImpl(categoryMenu);
        this.bankingTable = bankingTableImpl;
        this.tableAutomator = bankingTableImpl;
        this.storeChangeListeners = new ArrayList<>();
    }

    public JScrollPane getPane() {
        return bankingTable.getPane();
    }

    public BankingTableTester getTableTester() {
        return tableAutomator;
    }

    public void userCategorizesTransaction(int row, String categoryName) {
        for (Transaction transaction : bankingTable.getTransaction(row)) {
            transactionStore.categorizeTransaction(transaction, categoryName);
            notifyStoreChange(transaction.getWhichMonth());
        }
    }

    protected void notifyStoreChange(WhichMonth selectedMonth) {
        for (StoreChangeListener storeChangeListener : storeChangeListeners) {
            storeChangeListener.update(selectedMonth);
        }
    }

    public void update(WhichMonth selectedDate) {
        ArrayList<BankingTransaction> bankingTransactions = transactionStore.getBankingTransactions(selectedDate);
        bankingTable.display(bankingTransactions);
    }

    public void addStoreChangeListener(StoreChangeListener storeChangeListener){
        storeChangeListeners.add(storeChangeListener);
    }
}
