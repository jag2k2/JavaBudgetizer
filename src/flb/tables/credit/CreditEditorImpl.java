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

public class CreditEditorImpl implements CreditEditorAutomator, TransactionCategorizer {
    private final TransactionStore transactionStoreEditor;
    private final CreditTableImpl creditTable;
    private final CreditTableAutomator tableAutomator;

    public CreditEditorImpl(TransactionStore transactionStoreEditor){
        this.transactionStoreEditor = transactionStoreEditor;
        CreditTableImpl creditTableImpl = new CreditTableImpl();
        this.creditTable = creditTableImpl;
        this.tableAutomator = creditTableImpl;
    }

    public JScrollPane getPane() { return creditTable.getPane(); }

    public CreditTableAutomator getTableAutomator() { return tableAutomator; }

    public void refreshAndClearSelection(WhichMonth searchDate) {
        ArrayList<CreditTransaction> creditTransactions = transactionStoreEditor.getCreditTransactions(searchDate);
        creditTable.displayAndClearSelection(creditTransactions);
    }

    @Override
    public void addCategorizingListener(CategoryMenuImpl categoryMenuImpl) {
        creditTable.addCategoryClickedListener(new UserClicksTableListener("credit", categoryMenuImpl));
    }

    @Override
    public Maybe<Transaction> getTransaction(int row) {
        return creditTable.getTransaction(row);
    }
}
