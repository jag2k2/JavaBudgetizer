package flb.components.editors;

import flb.components.editors.tables.*;
import flb.datastores.*;

import javax.swing.*;

public class GoalEditorImpl {
    private final GoalStore goalStore;
    private final GoalTable goalTable;

    public GoalEditorImpl(GoalStore goalStore){
        this.goalStore = goalStore;
        GoalTableImp goalTable = new GoalTableImp();
        this.goalTable = goalTable;
    }

    public JScrollPane getPane() {
        return goalTable.getPane();
    }
}
