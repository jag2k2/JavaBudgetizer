package flb.listeners;

import flb.components.editors.MonthGoalEditor;
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
