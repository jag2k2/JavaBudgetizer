package com.jag2k2.listeners;

import com.jag2k2.components.editor.category.CategoryClearer;

import java.awt.event.*;

public class UserClearsDefaultGoalListener implements ActionListener {
    private final CategoryClearer categoryClearer;

    public UserClearsDefaultGoalListener(CategoryClearer categoryClearer) {
        this.categoryClearer = categoryClearer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int activeRow = Integer.parseInt(e.getActionCommand());
        categoryClearer.clearGoalAmount(activeRow);
    }
}
