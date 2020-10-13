package flb.category.application.listeners;

import flb.category.application.*;
import flb.category.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class UserClearsGoalListener implements ActionListener {
    private final CategoryStorage categoryStorage;
    private final CategoryTable categoryTable;

    public UserClearsGoalListener(CategoryStorage categoryStorage, CategoryTable categoryTable) {
        this.categoryStorage = categoryStorage;
        this.categoryTable = categoryTable;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String categoryToClear = categoryTable.getSelectedRowName();
        categoryStorage.updateAmount(categoryToClear, Float.NaN);
        ArrayList<Category> categories = categoryStorage.getCategories(categoryTable.getFilterText());
        categoryTable.editRefresh(categories);
    }
}
