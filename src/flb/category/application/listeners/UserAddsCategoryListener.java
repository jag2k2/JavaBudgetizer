package flb.category.application.listeners;

import flb.category.application.*;
import flb.category.*;

import javax.swing.*;
import java.util.*;
import java.awt.event.*;

public class UserAddsCategoryListener implements ActionListener {
    private final CategoryStorage categoryStorage;
    final CategoryTable categoryTable;
    private final JTextField nameFilter;

    public UserAddsCategoryListener(CategoryStorage categoryStorage, CategoryTable categoryTable, JTextField nameFilter){
        this.categoryStorage = categoryStorage;
        this.categoryTable = categoryTable;
        this.nameFilter = nameFilter;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String categoryToAdd = nameFilter.getText();
        if(!categoryToAdd.equals("") && !categoryStorage.categoryExist(categoryToAdd)) {
            categoryStorage.addCategory(categoryToAdd);
            nameFilter.setText("");
            ArrayList<Category> categories = categoryStorage.getCategories(nameFilter.getText());
            categoryTable.rowChangeRefresh(categories);
        }
    }
}
