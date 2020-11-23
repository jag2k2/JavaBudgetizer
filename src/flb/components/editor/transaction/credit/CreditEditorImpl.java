package flb.components.editor.transaction.credit;

import flb.components.editor.*;
import flb.components.editor.summary.SummarySelector;
import flb.components.editor.transaction.TableHighlighter;
import flb.components.editor.transaction.TransactionCategorizer;
import flb.components.menus.*;
import flb.components.monthselector.*;
import flb.datastores.*;
import flb.tuples.*;

import javax.swing.*;
import java.util.*;

public class CreditEditorImpl implements TransactionCategorizer, ViewChangeObserver,
        StoreChangeObserver, TableHighlighter {
    private final TransactionStore transactionStore;
    private final CreditTable creditTable;
    private final CreditTableTester tableAutomator;
    private final SelectedMonthGetter selectedMonthGetter;

    public CreditEditorImpl(TransactionStore transactionStore, CategoryStore categoryStore, SelectedMonthGetter selectedMonthGetter,
                            SummarySelector summarySelector){
        this.transactionStore = transactionStore;
        this.selectedMonthGetter = selectedMonthGetter;
        CategorizerMenuImpl categoryMenu = new CategorizerMenuImpl(categoryStore, this);
        CreditTableImpl creditTableImpl = new CreditTableImpl(categoryMenu, summarySelector);
        this.creditTable = creditTableImpl;
        this.tableAutomator = creditTableImpl;
    }

    public JScrollPane getPane() { return creditTable.getPane(); }

    public CreditTableTester getTableAutomator() { return tableAutomator; }

    public void userCategorizesTransaction(int row, String categoryName){
        for (Transaction transaction : creditTable.getTransaction(row)) {
            transactionStore.categorizeTransaction(transaction, categoryName);
        }
    }

    @Override
    public void update() {
        ArrayList<Transaction> creditTransactions = transactionStore.getCreditTransactions(selectedMonthGetter.getSelectedMonth());
        creditTable.displayAndClearSelection(creditTransactions);
    }

    @Override
    public void updateAndKeepSelection() {
        ArrayList<Transaction> creditTransactions = transactionStore.getCreditTransactions(selectedMonthGetter.getSelectedMonth());
        creditTable.displayAndClearSelection(creditTransactions);
    }

    @Override
    public void highlightRows() {
        creditTable.highlightRows();
    }
}
