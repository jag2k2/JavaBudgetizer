package flb.tables.banking;

import flb.application.main.listeners.*;
import flb.components.CategoryMenuImpl;
import flb.database.interfaces.*;
import flb.tables.banking.interfaces.*;
import flb.tables.interfaces.*;
import flb.tuples.*;
import flb.util.*;
import javax.swing.*;
import java.util.*;

public class BankingEditorImpl implements BankingEditorAutomator, TransactionCategorizer, MonthChangeListener, StoreChangeListener {
    private final TransactionStore transactionStore;
    private final BankingTableImpl bankingTable;
    private final BankingTableAutomator tableAutomator;
    private final ArrayList<StoreChangeListener> storeChangeListeners;

    public BankingEditorImpl(TransactionStore transactionStore){
        this.transactionStore = transactionStore;
        BankingTableImpl bankingTableImpl = new BankingTableImpl();
        this.bankingTable = bankingTableImpl;
        this.tableAutomator = bankingTableImpl;
        this.storeChangeListeners = new ArrayList<>();
    }

    public JScrollPane getPane() {
        return bankingTable.getPane();
    }

    public BankingTableAutomator getTableAutomator() {
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

    public void addCategoryColumnClickedListener(CategoryMenuImpl categoryMenuImpl) {
        bankingTable.addCategoryColumnClickedListener(new UserClicksTableListener(categoryMenuImpl));
    }

    public void addStoreChangeListener(StoreChangeListener storeChangeListener){
        storeChangeListeners.add(storeChangeListener);
    }
}
