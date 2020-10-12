package flb.category.application.listeners;

import flb.category.application.*;
import flb.category.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

public class UserFiltersCategoriesListener implements DocumentListener {
    private final CategoryStorage categoryStorage;
    private final JTextField nameFilter;
    private final CategoryTable categoryTable;

    public UserFiltersCategoriesListener(CategoryStorage categoryStorage, JTextField nameFilter, CategoryTable categoryTable) {
        this.categoryStorage = categoryStorage;
        this.nameFilter = nameFilter;
        this.categoryTable = categoryTable;
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        updateTableWithFilter();
    }

    @Override
    public void removeUpdate(DocumentEvent e){
        updateTableWithFilter();
    }

    private void updateTableWithFilter() {
        ArrayList<Category> categories = categoryStorage.getCategories(nameFilter.getText());
        categoryTable.rowChangeRefresh(categories);
    }
}

