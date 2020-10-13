package flb.category.application.listeners;

import flb.category.application.*;
import flb.category.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

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
        categoryTable.editRefresh(categories);
    }
}
