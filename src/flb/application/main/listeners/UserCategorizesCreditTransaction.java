package flb.application.main.listeners;

import flb.database.interfaces.*;
import java.awt.event.*;
import java.util.Calendar;

import flb.tables.credit.*;
import flb.tuples.*;
import flb.util.WhichMonth;

public class UserCategorizesCreditTransaction implements ActionListener {
    private final CreditEditorImpl creditEditor;
    private final TransactionStore transactionStore;

    public UserCategorizesCreditTransaction(CreditEditorImpl creditEditor, TransactionStore transactionStore){
        this.transactionStore = transactionStore;
        this.creditEditor = creditEditor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        String[] elements = actionCommand.split(",");
        int row = Integer.parseInt(elements[0]);
        String categoryName = elements[1];
        for(Transaction transaction : creditEditor.getTransaction(row)){
            transactionStore.categorizeTransaction(transaction, categoryName);
            creditEditor.refreshAndClearSelection(new WhichMonth(2020, Calendar.OCTOBER));
        }
    }
}
