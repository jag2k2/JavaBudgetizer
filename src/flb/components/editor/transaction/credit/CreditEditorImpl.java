package flb.components.editor.transaction.credit;

import flb.components.editor.*;
import flb.components.editor.summary.SummarySelector;
import flb.components.editor.transaction.TransactionCategorizer;
import flb.components.menus.*;
import flb.components.monthselector.*;
import flb.datastores.*;
import flb.tuples.*;
import flb.util.Transactions;
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
