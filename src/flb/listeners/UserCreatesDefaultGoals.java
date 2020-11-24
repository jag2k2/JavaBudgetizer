package flb.listeners;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import flb.components.monthselector.MonthDisplay;
import flb.datastores.GoalStore;

public class UserCreatesDefaultGoals implements ActionListener {
    private final GoalStore goalStore;
    private final MonthDisplay monthDisplay;
    private final Component component;

    public UserCreatesDefaultGoals(GoalStore goalStore, MonthDisplay monthDisplay, Component component)  {
        this.goalStore = goalStore;
        this.monthDisplay = monthDisplay;
        this.component = component;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        int goalCount = goalStore.countGoals(monthDisplay.getMonth());
        if (goalCount > 0) {
            int confirmation = getConfirmationFromDialog(goalCount, component);
            if(confirmation == JOptionPane.YES_OPTION) {
                goalStore.createDefaultGoals(monthDisplay.getMonth());
            }
        }
        else {
            goalStore.createDefaultGoals(monthDisplay.getMonth());
        }
    }

    protected int getConfirmationFromDialog(int goalCount, Component component) {
        return JOptionPane.showConfirmDialog(component, "This operation will delete " + goalCount + " existing goals " +
                "for the month.  Are you sure you want to continue?", "Confirm Operation", JOptionPane.YES_NO_OPTION);
    }
}
