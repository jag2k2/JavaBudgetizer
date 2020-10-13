package flb.category.application.listeners;

import flb.category.application.*;
import flb.category.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class UserDeletesCategoryListener implements ActionListener {

    private final CategoryStorage categoryStorage;
    private final CategoryTable categoryTable;
    private final JTextField nameFilter;
    private final JFrame frame;

    public UserDeletesCategoryListener(CategoryStorage categoryStorage, CategoryTable categoryTable, JTextField nameFilter, JFrame frame){
        this.categoryStorage = categoryStorage;
        this.categoryTable = categoryTable;
        this.nameFilter = nameFilter;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String categoryNameToDelete = categoryTable.getSelectedRowName();
        int selection = JOptionPane.showConfirmDialog(
                frame, "Are you sure you want to delete " + categoryNameToDelete + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (selection == JOptionPane.YES_OPTION) {
            categoryStorage.deleteCategory(categoryNameToDelete);
            ArrayList<Category> categories = categoryStorage.getCategories(nameFilter.getText());
            categoryTable.rowChangeRefresh(categories);
        }
    }
}
