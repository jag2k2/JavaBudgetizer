package flb.tables.credit;

import flb.application.main.listeners.*;
import flb.components.*;
import flb.database.interfaces.*;
import flb.tables.credit.interfaces.*;
import flb.tables.interfaces.*;
import flb.tuples.*;
import flb.util.*;
import javax.swing.*;
import java.util.*;

public class CreditEditorImpl implements CreditEditorAutomator, TransactionCategorizer, MonthChangeListener, StoreChangeListener {
    private final TransactionStore transactionStore;
    private final CreditTableImpl creditTable;
    private final CreditTableAutomator tableAutomator;
    private final ArrayList<StoreChangeListener> storeChangeListeners;

    public CreditEditorImpl(TransactionStore transactionStore){
        this.transactionStore = transactionStore;
        CreditTableImpl creditTableImpl = new CreditTableImpl();
        this.creditTable = creditTableImpl;
        this.tableAutomator = creditTableImpl;
        this.storeChangeListeners = new ArrayList<>();
    }

    public JScrollPane getPane() { return creditTable.getPane(); }

    public CreditTableAutomator getTableAutomator() { return tableAutomator; }

    public void userCategorizesTransaction(int row, String categoryName){
        for (Transaction transaction : creditTable.getTransaction(row)) {
            transactionStore.categorizeTransaction(transaction, categoryName);
            WhichMonth selectedMonth = transaction.getWhichMonth();
            notifyStoreChange(selectedMonth);
        }
    }

    protected void notifyStoreChange(WhichMonth selectedMonth) {
        for(StoreChangeListener storeChangeListener : storeChangeListeners) {
            storeChangeListener.update(selectedMonth);
        }
    }

    public void update(WhichMonth searchDate) {
        ArrayList<CreditTransaction> creditTransactions = transactionStore.getCreditTransactions(searchDate);
        creditTable.displayAndClearSelection(creditTransactions);
    }

    @Override
    public void addCategoryColumnClickedListener(CategoryMenuImpl categoryMenuImpl) {
        creditTable.addCategoryColumnClickedListener(new UserClicksTableListener(categoryMenuImpl));
    }

    public void addStoreChangeListener(StoreChangeListener storeChangeListener) {
        storeChangeListeners.add(storeChangeListener);
    }
}
