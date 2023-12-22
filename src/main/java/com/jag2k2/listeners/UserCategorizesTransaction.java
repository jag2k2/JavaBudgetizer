package com.jag2k2.listeners;

import java.awt.event.*;
import com.jag2k2.components.editor.transaction.TransactionCategorizer;

public class UserCategorizesTransaction implements ActionListener {
    private final TransactionCategorizer transactionCategorizer;

    public UserCategorizesTransaction(TransactionCategorizer transactionCategorizer){
        this.transactionCategorizer = transactionCategorizer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        String[] elements = actionCommand.split(",");
        int row = Integer.parseInt(elements[0]);
        String categoryName = elements[1];
        transactionCategorizer.categorizeTransaction(row, categoryName);
    }
}
