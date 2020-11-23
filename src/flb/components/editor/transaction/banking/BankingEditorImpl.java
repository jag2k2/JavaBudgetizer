package flb.components.editor.transaction.banking;

import flb.components.editor.*;
import flb.components.editor.summary.*;
import flb.components.editor.transaction.*;
import flb.components.menus.*;
import flb.components.monthselector.*;
import flb.datastores.*;
import flb.tuples.*;
import javax.swing.*;
import java.util.*;

public class BankingEditorImpl implements TransactionCategorizer, ViewChangeObserver,
        StoreChangeObserver, TableHighlighter {
    private final TransactionStore transactionStore;
    private final SelectedMonthGetter selectedMonthGetter;
    private final BankingTable bankingTable;
    private final BankingTableTester tableAutomator;

    public BankingEditorImpl(TransactionStore transactionStore, CategoryStore categoryStore, SelectedMonthGetter selectedMonthGetter,
                             SummarySelector summarySelector) {
        this.transactionStore = transactionStore;
        this.selectedMonthGetter = selectedMonthGetter;
        CategorizerMenuImpl categoryMenu = new CategorizerMenuImpl(categoryStore, this);
        BankingTableImpl bankingTableImpl = new BankingTableImpl(categoryMenu, summarySelector);
        this.bankingTable = bankingTableImpl;
        this.tableAutomator = bankingTableImpl;
    }

    public JScrollPane getPane() {
        return bankingTable.getPane();
    }

    public BankingTableTester getTableTester() {
        return tableAutomator;
    }

    @Override
    public void userCategorizesTransaction(int row, String categoryName) {
        for (Transaction transaction : bankingTable.getTransaction(row)) {
            transactionStore.categorizeTransaction(transaction, categoryName);
        }
    }

    @Override
    public void update() {
        ArrayList<Transaction> bankingTransactions = transactionStore.getBankingTransactions(selectedMonthGetter.getSelectedMonth());
        bankingTable.display(bankingTransactions);
    }

    @Override
    public void updateAndKeepSelection() {
        ArrayList<Transaction> bankingTransactions = transactionStore.getBankingTransactions(selectedMonthGetter.getSelectedMonth());
        bankingTable.display(bankingTransactions);
    }

    @Override
    public void highlightRows() {
        bankingTable.highlightRows();
    }
}
