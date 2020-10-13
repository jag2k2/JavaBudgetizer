package flb.category.application.listeners;

import flb.category.application.*;
import flb.category.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

public class UserFiltersCategoriesListener implements DocumentListener {
    private final CategoryStorage categoryStorage;
    private final CategoryTable categoryTable;
    private final JTextField nameFilter;

    public UserFiltersCategoriesListener(CategoryStorage categoryStorage, CategoryTable categoryTable, JTextField nameFilter) {
        this.categoryStorage = categoryStorage;
        this.categoryTable = categoryTable;
        this.nameFilter = nameFilter;
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

