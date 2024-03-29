package com.jag2k2.listeners;

import com.jag2k2.components.editor.summary.MonthGoalClearer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserClearsSummaryGoalListener implements ActionListener {
    private final MonthGoalClearer goalClearer;

    public UserClearsSummaryGoalListener(MonthGoalClearer goalClear){
        this.goalClearer = goalClear;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int activeRow = Integer.parseInt(e.getActionCommand());
        goalClearer.clearGoalAmount(activeRow);
    }
}
