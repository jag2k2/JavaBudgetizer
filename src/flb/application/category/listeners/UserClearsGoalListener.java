package flb.application.category.listeners;

import flb.tables.category.CategoryClearer;

import javax.swing.*;
import java.awt.event.*;

public class UserClearsGoalListener implements ActionListener {
    private final CategoryClearer categoryClearer;
    private final JTextField nameFilter;

    public UserClearsGoalListener(CategoryClearer categoryClearer, JTextField nameFilter) {
        this.categoryClearer = categoryClearer;
        this.nameFilter = nameFilter;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        categoryClearer.userClearSelectedGoalAmount();
        categoryClearer.refreshAndKeepSelection(nameFilter.getText());
    }
}
