package flb.components.editors;

import flb.components.menus.*;
import flb.datastores.*;
import flb.components.editors.tables.*;
import flb.tuples.*;
import flb.util.*;
import javax.swing.*;
import java.util.*;

public class BankingEditorImpl implements BankingEditorTester, TransactionCategorizer, MonthChangeListener, StoreChangeListener, GoalSelectedListener {
    private final TransactionStore transactionStore;
    private final BankingTable bankingTable;
    private final BankingTableTester tableAutomator;
    private final ArrayList<StoreChangeListener> storeChangeListeners;

    public BankingEditorImpl(TransactionStore transactionStore, CategoryStore categoryStore, GoalSelector goalSelector) {
        this.transactionStore = transactionStore;
        CategorizerMenuImpl categoryMenu = new CategorizerMenuImpl(categoryStore, this);
        BankingTableImpl bankingTableImpl = new BankingTableImpl(categoryMenu, goalSelector);
        this.bankingTable = bankingTableImpl;
        this.tableAutomator = bankingTableImpl;
        this.storeChangeListeners = new ArrayList<>();
    }

    public JScrollPane getPane() {
        return bankingTable.getPane();
    }

    @Override
    public BankingTableTester getTableTester() {
        return tableAutomator;
    }

    @Override
    public void userCategorizesTransaction(int row, String categoryName) {
        for (Transaction transaction : bankingTable.getTransaction(row)) {
            transactionStore.categorizeTransaction(transaction, categoryName);
            notifyStoreChange(transaction.getWhichMonth());
        }
    }

    protected void notifyStoreChange(WhichMonth selectedMonth) {
        for (StoreChangeListener storeChangeListener : storeChangeListeners) {
            storeChangeListener.updateAndKeepSelection(selectedMonth);
        }
    }

    public void addStoreChangeListener(StoreChangeListener storeChangeListener){
        storeChangeListeners.add(storeChangeListener);
    }

    @Override
    public void update(WhichMonth selectedDate) {
        ArrayList<BankingTransaction> bankingTransactions = transactionStore.getBankingTransactions(selectedDate);
        bankingTable.display(bankingTransactions);
    }

    @Override
    public void updateAndKeepSelection(WhichMonth selectedDate) {
        ArrayList<BankingTransaction> bankingTransactions = transactionStore.getBankingTransactions(selectedDate);
        bankingTable.display(bankingTransactions);
    }

    @Override
    public void renderTable() {
        bankingTable.renderTable();
    }
}
