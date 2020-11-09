package flb.listeners;

import flb.components.editors.CategoryDeleter;

import java.awt.event.*;
import javax.swing.*;


public class UserDeletesCategoryListener implements ActionListener {
    private final CategoryDeleter categoryDeleter;
    private final JTextField nameFilter;
    private final JFrame frame;

    public UserDeletesCategoryListener(CategoryDeleter categoryDeleter, JTextField nameFilter, JFrame frame){
        this.categoryDeleter = categoryDeleter;
        this.nameFilter = nameFilter;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int activeRow = Integer.parseInt(e.getActionCommand());
        categoryDeleter.userDeleteCategory(activeRow, frame);
        categoryDeleter.refreshAndClearSelection(nameFilter.getText());
    }
}
