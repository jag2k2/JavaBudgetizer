package flb.application.main.listeners;

import flb.database.interfaces.*;
import java.awt.event.*;
import java.util.Calendar;

import flb.tables.banking.*;
import flb.tuples.*;
import flb.util.WhichMonth;

public class UserCategorizesBankingTransaction implements ActionListener {
    private final BankingEditorImpl bankingEditor;
    private final TransactionStore transactionStore;

    public UserCategorizesBankingTransaction(BankingEditorImpl bankingEditor, TransactionStore transactionStore){
        this.transactionStore = transactionStore;
        this.bankingEditor = bankingEditor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        String[] elements = actionCommand.split(",");
        int row = Integer.parseInt(elements[0]);
        String categoryName = elements[1];
        for(Transaction transaction : bankingEditor.getTransaction(row)){
            transactionStore.categorizeTransaction(transaction, categoryName);
            bankingEditor.refresh(new WhichMonth(2020, Calendar.OCTOBER));
        }
    }
}
