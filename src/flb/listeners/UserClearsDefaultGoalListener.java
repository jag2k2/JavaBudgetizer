package flb.listeners;

import flb.components.editors.CategoryClearer;
import javax.swing.*;
import java.awt.event.*;

public class UserClearsDefaultGoalListener implements ActionListener {
    private final CategoryClearer categoryClearer;

    public UserClearsDefaultGoalListener(CategoryClearer categoryClearer) {
        this.categoryClearer = categoryClearer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int activeRow = Integer.parseInt(e.getActionCommand());
        categoryClearer.userClearGoalAmount(activeRow);
    }
}
