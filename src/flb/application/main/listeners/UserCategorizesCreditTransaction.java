package flb.application.main.listeners;

import java.awt.event.*;
import java.util.Calendar;
import flb.tables.credit.*;
import flb.util.WhichMonth;

public class UserCategorizesCreditTransaction implements ActionListener {
    private final CreditEditorImpl creditEditor;

    public UserCategorizesCreditTransaction(CreditEditorImpl creditEditor){
        this.creditEditor = creditEditor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        String[] elements = actionCommand.split(",");
        int row = Integer.parseInt(elements[0]);
        String categoryName = elements[1];
        creditEditor.userCategorizesTransaction(row, categoryName);
        creditEditor.refreshAndKeepSelection(new WhichMonth(2020, Calendar.OCTOBER));
    }
}
