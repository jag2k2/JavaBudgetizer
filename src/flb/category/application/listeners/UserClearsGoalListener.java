package flb.category.application.listeners;

import flb.category.Category;
import flb.category.CategoryStorage;
import flb.category.application.CategoryTable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UserClearsGoalListener implements ActionListener {
    private final CategoryStorage categoryStorage;
    private final CategoryTable categoryTable;
    private final JTextField nameFilter;

    public UserClearsGoalListener(CategoryStorage categoryStorage, CategoryTable categoryTable, JTextField nameFilter) {
        this.categoryStorage = categoryStorage;
        this.categoryTable = categoryTable;
        this.nameFilter = nameFilter;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String categoryToClear = categoryTable.getSelectedRowName();
        categoryStorage.updateAmount(categoryToClear, Float.NaN);
        ArrayList<Category> categories = categoryStorage.getCategories(nameFilter.getText());
        categoryTable.refresh(categories);
    }
}
