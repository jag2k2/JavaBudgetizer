package flb.listeners;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import flb.components.monthselector.SelectedMonthGetter;
import flb.datastores.GoalStore;

public class UserCreatesDefaultGoals implements ActionListener {
    private final GoalStore goalStore;
    private final SelectedMonthGetter selectedMonthGetter;
    private final Component component;

    public UserCreatesDefaultGoals(GoalStore goalStore, SelectedMonthGetter selectedMonthGetter, Component component)  {
        this.goalStore = goalStore;
        this.selectedMonthGetter = selectedMonthGetter;
        this.component = component;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        int goalCount = goalStore.countGoals(selectedMonthGetter.getSelectedMonth());
        if (goalCount > 0) {
            int confirmation = getConfirmationFromDialog(goalCount, component);
            if(confirmation == JOptionPane.YES_OPTION) {
                goalStore.createDefaultGoals(selectedMonthGetter.getSelectedMonth());
            }
        }
        else {
            goalStore.createDefaultGoals(selectedMonthGetter.getSelectedMonth());
        }
    }

    protected int getConfirmationFromDialog(int goalCount, Component component) {
        return JOptionPane.showConfirmDialog(component, "This operation will delete " + goalCount + " existing goals " +
                "for the month.  Are you sure you want to continue?", "Confirm Operation", JOptionPane.YES_NO_OPTION);
    }
}
