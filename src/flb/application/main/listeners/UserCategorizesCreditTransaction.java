package flb.application.main.listeners;

import java.awt.event.*;
import flb.components.MonthSelectorImpl;
import flb.tables.credit.*;

public class UserCategorizesCreditTransaction implements ActionListener {
    private final CreditEditorImpl creditEditor;
    private final MonthSelectorImpl monthSelector;

    public UserCategorizesCreditTransaction(CreditEditorImpl creditEditor, MonthSelectorImpl monthSelector){
        this.creditEditor = creditEditor;
        this.monthSelector = monthSelector;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        String[] elements = actionCommand.split(",");
        int row = Integer.parseInt(elements[0]);
        String categoryName = elements[1];
        creditEditor.userCategorizesTransaction(row, categoryName);
        creditEditor.update(monthSelector.getSelectedMonth());
    }
}
