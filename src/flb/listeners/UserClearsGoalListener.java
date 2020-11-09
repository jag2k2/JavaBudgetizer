package flb.listeners;

import flb.components.editors.CategoryClearer;
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
        int activeRow = Integer.parseInt(e.getActionCommand());
        categoryClearer.userClearGoalAmount(activeRow);
        categoryClearer.refreshAndKeepSelection(nameFilter.getText());
    }
}
