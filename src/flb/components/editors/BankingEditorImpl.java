package flb.components.editors;

import flb.components.*;
import flb.components.menus.*;
import flb.components.monthselector.*;
import flb.datastores.*;
import flb.components.editors.tables.*;
import flb.tuples.*;

import javax.swing.*;
import java.util.*;

public class BankingEditorImpl implements BankingEditorTester, TransactionCategorizer, MonthChangeObserver,
        StoreChangeObserver, TableHighlighter, StoreChanger {
    private final TransactionStore transactionStore;
    private final SelectedMonthGetter selectedMonthGetter;
    private final BankingTable bankingTable;
    private final BankingTableTester tableAutomator;
    private final ArrayList<StoreChangeObserver> storeChangeObservers;

    public BankingEditorImpl(TransactionStore transactionStore, CategoryStore categoryStore, SelectedMonthGetter selectedMonthGetter,
                             SummarySelector summarySelector) {
        this.transactionStore = transactionStore;
        this.selectedMonthGetter = selectedMonthGetter;
        CategorizerMenuImpl categoryMenu = new CategorizerMenuImpl(categoryStore, this);
        BankingTableImpl bankingTableImpl = new BankingTableImpl(categoryMenu, summarySelector);
        this.bankingTable = bankingTableImpl;
        this.tableAutomator = bankingTableImpl;
        this.storeChangeObservers = new ArrayList<>();
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
            notifyStoreChange();
        }
    }

    @Override
    public void notifyStoreChange() {
        for (StoreChangeObserver storeChangeObserver : storeChangeObservers) {
            storeChangeObserver.updateAndKeepSelection();
        }
    }

    @Override
    public void addStoreChangeObserver(StoreChangeObserver storeChangeObserver){
        storeChangeObservers.add(storeChangeObserver);
    }

    @Override
    public void update() {
        ArrayList<BankingTransaction> bankingTransactions = transactionStore.getBankingTransactions(selectedMonthGetter.getSelectedMonth());
        bankingTable.display(bankingTransactions);
    }

    @Override
    public void updateAndKeepSelection() {
        ArrayList<BankingTransaction> bankingTransactions = transactionStore.getBankingTransactions(selectedMonthGetter.getSelectedMonth());
        bankingTable.display(bankingTransactions);
    }

    @Override
    public void highlightRows() {
        bankingTable.highlightRows();
    }
}
