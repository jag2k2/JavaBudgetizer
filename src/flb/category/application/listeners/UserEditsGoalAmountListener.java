package flb.category.application.listeners;

import flb.category.application.*;

import javax.swing.event.*;

public class UserEditsGoalAmountListener implements TableModelListener {
    private final CategoryTableEditor tableEditor;

    public UserEditsGoalAmountListener(CategoryTableEditor tableEditor) {
        this.tableEditor = tableEditor;
    }

    @Override
    public void tableChanged(TableModelEvent event) {
            tableEditor.userEditsGoalAmount(event.getColumn());
    }
}
