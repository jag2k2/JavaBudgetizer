package flb.application.main.listeners;

import java.awt.event.*;
import flb.components.MonthSelectorImpl;
import flb.tables.banking.*;

public class UserCategorizesBankingTransaction implements ActionListener {
    private final BankingEditorImpl bankingEditor;
    private final MonthSelectorImpl monthSelector;

    public UserCategorizesBankingTransaction(BankingEditorImpl bankingEditor, MonthSelectorImpl monthSelector){
        this.bankingEditor = bankingEditor;
        this.monthSelector = monthSelector;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        String[] elements = actionCommand.split(",");
        int row = Integer.parseInt(elements[0]);
        String categoryName = elements[1];
        bankingEditor.userCategorizesTransaction(row, categoryName);
        bankingEditor.update(monthSelector.getSelectedMonth());
    }
}
