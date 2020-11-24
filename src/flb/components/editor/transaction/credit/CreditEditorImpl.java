package flb.components.editor.transaction.credit;

import flb.components.editor.*;
import flb.components.editor.summary.SummarySelector;
import flb.components.editor.transaction.TableHighlighter;
import flb.components.editor.transaction.TransactionCategorizer;
import flb.components.menus.*;
import flb.components.monthselector.*;
import flb.datastores.*;
import flb.tuples.*;
import flb.util.WhichMonth;

import javax.swing.*;
import java.util.*;

public class CreditEditorImpl implements TransactionCategorizer, TransactionGrouper, ViewChangeObserver,
        StoreChangeObserver, TableHighlighter {
    private final TransactionStore transactionStore;
    private final CreditTable creditTable;
    private final CreditTableTester tableAutomator;
    private final MonthDisplay monthDisplay;

    public CreditEditorImpl(TransactionStore transactionStore, CategoryStore categoryStore, MonthDisplay monthDisplay,
                            SummarySelector summarySelector){
        this.transactionStore = transactionStore;
        this.monthDisplay = monthDisplay;
        CategorizerMenuImpl categoryMenu = new CategorizerMenuImpl(categoryStore, this);
        GrouperMenuImpl grouperMenu = new GrouperMenuImpl(this);
        CreditTableImpl creditTableImpl = new CreditTableImpl(categoryMenu, grouperMenu, summarySelector);
        this.creditTable = creditTableImpl;
        this.tableAutomator = creditTableImpl;
    }

    public JPanel getPanel() { return creditTable.getPanel(); }

    public CreditTableTester getTableAutomator() { return tableAutomator; }

    @Override
    public void userCategorizesTransaction(int row, String categoryName){
        for (Transaction transaction : creditTable.getTransaction(row)) {
            transactionStore.categorizeTransaction(transaction, categoryName);
        }
    }

    @Override
    public void groupSelectedTransactions(Calendar date) {
        String groupName = createGroupName(date, creditTable.getSelectedSum());
        transactionStore.labelGroup(creditTable.getSelectedTransactions(), groupName);
    }

    protected String createGroupName(Calendar date, float sum){
        String groupName = "$yr-$mo-$day-$hr$min:$sum";
        groupName = groupName.replace("$yr", Integer.toString(date.get(Calendar.YEAR)));
        groupName = groupName.replace("$mo", String.format("%02d", date.get(Calendar.MONTH)+1));
        groupName = groupName.replace("$day", String.format("%02d", date.get(Calendar.DAY_OF_MONTH)));
        groupName = groupName.replace("$hr", String.format("%02d", date.get(Calendar.HOUR_OF_DAY)));
        groupName = groupName.replace("$min", String.format("%02d", date.get(Calendar.MINUTE)));
        groupName = groupName.replace("$sum", String.format("%.2f", sum));
        return groupName;
    }

    @Override
    public void update() {
        ArrayList<Transaction> creditTransactions = transactionStore.getCreditTransactions(monthDisplay.getMonth());
        creditTable.display(creditTransactions);
    }

    @Override
    public void updateAndKeepSelection() {
        ArrayList<Transaction> creditTransactions = transactionStore.getCreditTransactions(monthDisplay.getMonth());
        creditTable.displayAndKeepSelection(creditTransactions);
    }

    @Override
    public void highlightRows() {
        creditTable.highlightRows();
    }
}
