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
    private final MonthDisplay monthDisplay;
    private final TransactionTable bankingTable;
    private final TransactionTableTester tableAutomator;

    public BankingEditorImpl(TransactionStore transactionStore, CategoryStore categoryStore, MonthDisplay monthDisplay,
                             SummarySelector summarySelector) {
        this.transactionStore = transactionStore;
        this.monthDisplay = monthDisplay;
        CategorizerMenuImpl categoryMenu = new CategorizerMenuImpl(categoryStore, this);
        BankingTableImpl bankingTableImpl = new BankingTableImpl(categoryMenu, summarySelector);
        this.bankingTable = bankingTableImpl;
        this.tableAutomator = bankingTableImpl;
    }

    public JPanel getPanel() {
        return bankingTable.getPanel();
    }

    public TransactionTableTester getTableTester() {
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
        ArrayList<Transaction> bankingTransactions = transactionStore.getBankingTransactions(monthDisplay.getMonth());
        bankingTable.display(bankingTransactions);
    }

    @Override
    public void updateAndKeepSelection() {
        ArrayList<Transaction> bankingTransactions = transactionStore.getBankingTransactions(monthDisplay.getMonth());
        bankingTable.display(bankingTransactions);
    }

    @Override
    public void highlightRows() {
        bankingTable.highlightRows();
    }
}
