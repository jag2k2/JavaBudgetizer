package flb.application.main.listeners;

import java.awt.event.*;
import java.util.Calendar;
import flb.tables.banking.*;
import flb.util.WhichMonth;

public class UserCategorizesBankingTransaction implements ActionListener {
    private final BankingEditorImpl bankingEditor;

    public UserCategorizesBankingTransaction(BankingEditorImpl bankingEditor){
        this.bankingEditor = bankingEditor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        String[] elements = actionCommand.split(",");
        int row = Integer.parseInt(elements[0]);
        String categoryName = elements[1];
        bankingEditor.userCategorizesTransaction(row, categoryName);
        bankingEditor.refresh(new WhichMonth(2020, Calendar.OCTOBER));
    }
}
