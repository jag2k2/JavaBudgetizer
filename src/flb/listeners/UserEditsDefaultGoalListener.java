package flb.listeners;

import flb.components.editors.CategoryGoalEditor;

import javax.swing.*;
import javax.swing.event.*;

public class UserEditsDefaultGoalListener implements TableModelListener {
    private final CategoryGoalEditor amountEditor;
    private final JTextField nameFilter;

    public UserEditsDefaultGoalListener(CategoryGoalEditor amountEditor, JTextField nameFilter) {
        this.amountEditor = amountEditor;
        this.nameFilter = nameFilter;
    }

    @Override
    public void tableChanged(TableModelEvent event) {
        if(event.getColumn() == 1) {
            amountEditor.UpdateSelectedGoalAmount();
            amountEditor.refreshAndKeepSelection(nameFilter.getText());
        }
    }
}
