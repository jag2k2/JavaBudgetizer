package com.jag2k2.components.editor.transaction.credit;

import com.jag2k2.components.editor.*;
import com.jag2k2.components.editor.summary.SummarySelector;
import com.jag2k2.components.editor.transaction.TransactionCategorizer;
import com.jag2k2.components.menus.*;
import com.jag2k2.components.monthselector.*;
import com.jag2k2.datastores.*;
import com.jag2k2.tuples.*;
import com.jag2k2.util.Transactions;
import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class CreditEditorImpl extends JComponent implements TransactionCategorizer, TransactionGrouper, ViewChangeObserver,
        StoreChangeObserver {
    private final CreditStore creditStore;
    private final CreditTable creditTable;
    private final CreditTableTester tableAutomator;
    private final MonthDisplay monthDisplay;

    public CreditEditorImpl(CreditStore creditStore, MonthDisplay monthDisplay, SummarySelector summarySelector){
        this.creditStore = creditStore;
        this.monthDisplay = monthDisplay;
        CategorizerMenuImpl categoryMenu = new CategorizerMenuImpl(creditStore.getCategories(""), this);
        GrouperMenuImpl grouperMenu = new GrouperMenuImpl(this);
        CreditTableImpl creditTableImpl = new CreditTableImpl(categoryMenu, grouperMenu, summarySelector);
        this.creditTable = creditTableImpl;
        this.tableAutomator = creditTableImpl;
        this.setLayout(new BorderLayout());
        this.add(creditTableImpl);

        creditStore.addStoreChangeObserver(this);
        monthDisplay.addViewChangeObserver(this);
    }

    public CreditTableTester getTableAutomator() { return tableAutomator; }

    @Override
    public void categorizeTransaction(int row, String categoryName){
        Transaction transaction = creditTable.getTransaction(row);
        creditStore.categorizeTransaction(transaction, categoryName);
    }

    @Override
    public void groupSelectedTransactions(Calendar date) {
        String groupName = GroupNameFactory.createGroupName(date, creditTable.getSelectedSum());
        creditStore.labelGroup(creditTable.getSelectedTransactions(), groupName);
    }

    @Override
    public void update() {
        Transactions<CreditTransaction> creditTransactions = creditStore.getCreditTransactions(monthDisplay.getMonth());
        creditTable.display(creditTransactions);
    }

    @Override
    public void updateAndKeepSelection() {
        Transactions<CreditTransaction> creditTransactions = creditStore.getCreditTransactions(monthDisplay.getMonth());
        creditTable.displayAndKeepSelection(creditTransactions);
    }
}
