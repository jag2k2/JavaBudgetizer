package com.jag2k2.listeners;

import com.jag2k2.components.editor.category.CategoryGoalEditor;
import javax.swing.event.*;

public class UserEditsDefaultGoalListener implements TableModelListener {
    private final CategoryGoalEditor amountEditor;

    public UserEditsDefaultGoalListener(CategoryGoalEditor amountEditor) {
        this.amountEditor = amountEditor;
    }

    @Override
    public void tableChanged(TableModelEvent event) {
        if(event.getColumn() == 1) {
            amountEditor.updateSelectedGoalAmount();
        }
    }
}
