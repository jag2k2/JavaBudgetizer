package flb.listeners;

import flb.components.editors.CategoryDeleter;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class UserDeletesCategoryListener implements ActionListener {
    private final CategoryDeleter categoryDeleter;
    private final JTextField nameFilter;
    private final Component component;

    public UserDeletesCategoryListener(CategoryDeleter categoryDeleter, JTextField nameFilter, Component component){
        this.categoryDeleter = categoryDeleter;
        this.nameFilter = nameFilter;
        this.component = component;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int activeRow = Integer.parseInt(e.getActionCommand());
        categoryDeleter.userDeleteCategory(activeRow, component);
        categoryDeleter.refreshAndClearSelection(nameFilter.getText());
    }
}
