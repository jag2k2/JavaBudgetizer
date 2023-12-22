package com.jag2k2.listeners;

import com.jag2k2.components.editor.summary.MonthGoalEditor;
import javax.swing.event.*;

public class UserEditsSummaryGoalListener implements TableModelListener {
    private final MonthGoalEditor goalEditor;

    public UserEditsSummaryGoalListener(MonthGoalEditor goalEditor){
        this.goalEditor = goalEditor;
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        if (e.getColumn() == 1) {
            goalEditor.updateSelectedGoalAmount();
        }
    }
}
