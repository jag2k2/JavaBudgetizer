package flb.category.application.listeners;

import flb.category.application.*;

import javax.swing.*;
import javax.swing.event.*;

public class UserEditsGoalAmountListener implements TableModelListener {
    private final CategoryTableEditor tableEditor;
    private final JTextField nameFilter;

    public UserEditsGoalAmountListener(CategoryTableEditor tableEditor, JTextField nameFilter) {
        this.tableEditor = tableEditor;
        this.nameFilter = nameFilter;
    }

    @Override
    public void tableChanged(TableModelEvent event) {
        if(event.getColumn() == 1) {
            tableEditor.userEditsSelectedGoalAmount();
            tableEditor.refreshAndKeepSelection(nameFilter.getText());
        }
    }
}
