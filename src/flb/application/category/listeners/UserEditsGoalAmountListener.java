package flb.application.category.listeners;

import flb.tables.category.interfaces.CategoryAmountEditor;

import javax.swing.*;
import javax.swing.event.*;

public class UserEditsGoalAmountListener implements TableModelListener {
    private final CategoryAmountEditor amountEditor;
    private final JTextField nameFilter;

    public UserEditsGoalAmountListener(CategoryAmountEditor amountEditor, JTextField nameFilter) {
        this.amountEditor = amountEditor;
        this.nameFilter = nameFilter;
    }

    @Override
    public void tableChanged(TableModelEvent event) {
        if(event.getColumn() == 1) {
            amountEditor.userEditsSelectedGoalAmount();
            amountEditor.refreshAndKeepSelection(nameFilter.getText());
        }
    }
}
