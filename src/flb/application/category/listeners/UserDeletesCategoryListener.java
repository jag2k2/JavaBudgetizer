package flb.application.category.listeners;

import flb.tables.category.CategoryDeleter;

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
        categoryDeleter.userDeleteSelectedCategory(frame);
        categoryDeleter.refreshAndClearSelection(nameFilter.getText());
    }
}
