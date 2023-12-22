package com.jag2k2.listeners;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.jag2k2.components.monthselector.MonthDisplay;
import com.jag2k2.datastores.DefaultGoalStoreCreator;

public class UserCreatesDefaultGoals implements ActionListener {
    private final DefaultGoalStoreCreator goalStoreCreator;
    private final MonthDisplay monthDisplay;
    private final Component component;

    public UserCreatesDefaultGoals(DefaultGoalStoreCreator goalStoreCreator, MonthDisplay monthDisplay, Component component)  {
        this.goalStoreCreator = goalStoreCreator;
        this.monthDisplay = monthDisplay;
        this.component = component;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        int goalCount = goalStoreCreator.countGoals(monthDisplay.getMonth());
        if (goalCount > 0) {
            int confirmation = getConfirmationFromDialog(goalCount, component);
            if(confirmation == JOptionPane.YES_OPTION) {
                goalStoreCreator.createDefaultGoals(monthDisplay.getMonth());
            }
        }
        else {
            goalStoreCreator.createDefaultGoals(monthDisplay.getMonth());
        }
    }

    protected int getConfirmationFromDialog(int goalCount, Component component) {
        return JOptionPane.showConfirmDialog(component, "This operation will delete " + goalCount + " existing goals " +
                "for the month.  Are you sure you want to continue?", "Confirm Operation", JOptionPane.YES_NO_OPTION);
    }
}
