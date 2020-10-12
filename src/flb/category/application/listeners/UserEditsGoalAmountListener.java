package flb.category.application.listeners;

import flb.category.application.*;
import flb.category.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.ArrayList;

public class UserEditsGoalAmountListener implements TableModelListener {
    private final CategoryStorage categoryStorage;
    private final JTextField nameFilter;
    private final CategoryTable categoryTable;

    public UserEditsGoalAmountListener(CategoryStorage categoryStorage, JTextField nameFilter, CategoryTable categoryTable) {
        this.categoryStorage = categoryStorage;
        this.nameFilter = nameFilter;
        this.categoryTable = categoryTable;
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        if (e.getColumn() == 1) {
            String categoryToUpdate = categoryTable.getSelectedRowName();
            categoryStorage.updateAmount(categoryToUpdate, categoryTable.getSelectedDefaultGoal());
            ArrayList<Category> categories = categoryStorage.getCategories(nameFilter.getText());
            categoryTable.editRefresh(categories);
        }
    }
}
