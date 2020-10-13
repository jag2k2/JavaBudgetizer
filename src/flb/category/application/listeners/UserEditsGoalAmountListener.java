package flb.category.application.listeners;

import flb.category.application.*;
import flb.category.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.ArrayList;

public class UserEditsGoalAmountListener implements TableModelListener {
    private final CategoryStorage categoryStorage;
    private final CategoryTable categoryTable;

    public UserEditsGoalAmountListener(CategoryStorage categoryStorage, CategoryTable categoryTable) {
        this.categoryStorage = categoryStorage;
        this.categoryTable = categoryTable;
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        if (e.getColumn() == 1) {
            String categoryToUpdate = categoryTable.getSelectedRowName();
            Float newAmount = categoryTable.getSelectedDefaultGoal();
            categoryStorage.updateAmount(categoryToUpdate, newAmount);
            ArrayList<Category> categories = categoryStorage.getCategories(categoryTable.getFilterText());
            categoryTable.editRefresh(categories);
        }
    }
}
