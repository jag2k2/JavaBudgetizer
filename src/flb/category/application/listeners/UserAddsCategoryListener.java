package flb.category.application.listeners;

import flb.category.application.*;
import flb.category.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class UserAddsCategoryListener implements ActionListener {
    private final CategoryStorage categoryStorage;
    private final CategoryTable categoryTable;

    public UserAddsCategoryListener(CategoryStorage categoryStorage, CategoryTable categoryTable){
        this.categoryStorage = categoryStorage;
        this.categoryTable = categoryTable;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String categoryToAdd = categoryTable.getFilterText();
        if(!categoryToAdd.equals("")) {
                if(!categoryStorage.categoryExist(categoryToAdd)) {
                    categoryStorage.addCategory(categoryToAdd);
                    categoryTable.clearFilterText();
                    ArrayList<Category> categories = categoryStorage.getCategories(categoryTable.getFilterText());
                    categoryTable.rowChangeRefresh(categories);

                }
        }
    }
}
