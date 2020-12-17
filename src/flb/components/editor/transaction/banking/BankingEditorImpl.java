package flb.components.editor.transaction.banking;

import flb.components.editor.*;
import flb.components.editor.summary.*;
import flb.components.editor.transaction.*;
import flb.components.menus.*;
import flb.components.monthselector.*;
import flb.datastores.*;
import flb.tuples.*;
import flb.util.Transactions;
import javax.swing.*;
import java.awt.*;

public class BankingEditorImpl extends JComponent implements TransactionCategorizer, ViewChangeObserver,
        StoreChangeObserver {
    private final BankingStore bankingStore;
    private final MonthDisplay monthDisplay;
    private final TransactionTable<BankingTransaction> bankingTable;
    private final TransactionTableTester tableAutomator;

    public BankingEditorImpl(BankingStore bankingStore, MonthDisplay monthDisplay, SummarySelector summarySelector) {
        this.bankingStore = bankingStore;
        this.monthDisplay = monthDisplay;
        CategorizerMenuImpl categoryMenu = new CategorizerMenuImpl(bankingStore.getCategories(""), this);
        BankingTableImpl bankingTableImpl = new BankingTableImpl(categoryMenu, summarySelector);
        this.bankingTable = bankingTableImpl;
        this.tableAutomator = bankingTableImpl;
        this.setLayout(new BorderLayout());
        this.add(bankingTableImpl);

        this.bankingStore.addStoreChangeObserver(this);
        monthDisplay.addViewChangeObserver(this);
    }

    public TransactionTableTester getTableTester() {
        return tableAutomator;
    }

    @Override
    public void categorizeTransaction(int row, String categoryName) {
        Transaction transaction = bankingTable.getTransaction(row);
        bankingStore.categorizeTransaction(transaction, categoryName);
    }

    @Override
    public void update() {
        Transactions<BankingTransaction> bankingTransactions = bankingStore.getBankingTransactions(monthDisplay.getMonth());
        bankingTable.display(bankingTransactions);
    }

    @Override
    public void updateAndKeepSelection() {
        Transactions<BankingTransaction> bankingTransactions = bankingStore.getBankingTransactions(monthDisplay.getMonth());
        bankingTable.display(bankingTransactions);
    }
}
