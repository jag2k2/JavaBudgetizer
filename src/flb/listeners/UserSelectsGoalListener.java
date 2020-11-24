package flb.listeners;

import flb.components.editor.summary.GoalSelectedNotifier;
import javax.swing.event.*;

public class UserSelectsGoalListener implements ListSelectionListener {
    private final GoalSelectedNotifier goalSelectedNotifier;

    public UserSelectsGoalListener(GoalSelectedNotifier goalSelectedNotifier){
        this.goalSelectedNotifier = goalSelectedNotifier;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            goalSelectedNotifier.notifyGoalSelected();
        }
    }
}
