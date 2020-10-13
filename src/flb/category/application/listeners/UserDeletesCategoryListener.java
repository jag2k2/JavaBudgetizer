package flb.category.application.listeners;

import flb.category.application.*;
import flb.category.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class UserDeletesCategoryListener implements ActionListener {

    private final CategoryStorage categoryStorage;
    private final CategoryTable categoryTable;

    public UserDeletesCategoryListener(CategoryStorage categoryStorage, CategoryTable categoryTable){
        this.categoryStorage = categoryStorage;
        this.categoryTable = categoryTable;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String categoryNameToDelete = categoryTable.getSelectedRowName();
        categoryStorage.deleteCategory(categoryNameToDelete);
        ArrayList<Category> categories = categoryStorage.getCategories(categoryTable.getFilterText());
        categoryTable.rowChangeRefresh(categories);
    }
}
