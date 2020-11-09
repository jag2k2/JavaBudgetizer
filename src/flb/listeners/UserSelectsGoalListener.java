package flb.listeners;

import flb.components.editors.GoalSelectedListener;
import java.util.*;
import javax.swing.event.*;

public class UserSelectsGoalListener implements ListSelectionListener {
    ArrayList<GoalSelectedListener> goalSelectedListeners;

    public UserSelectsGoalListener(ArrayList<GoalSelectedListener> goalSelectedListeners){
        this.goalSelectedListeners = goalSelectedListeners;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            for (GoalSelectedListener goalSelectedListener : goalSelectedListeners){
                goalSelectedListener.renderTable();
            }
        }
    }
}
