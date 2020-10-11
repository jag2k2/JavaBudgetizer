package flb.category.management;

import flb.category.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

class UserAddsCategoryListener implements ActionListener {
    private final CategoryStorage categoryStorage;
    private final CategoryTable categoryTable;
    private final JTextField nameFilter;

    public UserAddsCategoryListener(CategoryStorage categoryStorage, JTextField nameFilter, CategoryTable categoryTable){
        this.categoryStorage = categoryStorage;
        this.categoryTable = categoryTable;
        this.nameFilter = nameFilter;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String categoryToAdd = nameFilter.getText();
        if(!categoryStorage.categoryExist(categoryToAdd)) {
            categoryStorage.addCategory(categoryToAdd);
            ArrayList<Category> allCategories = categoryStorage.getCategories("");
            categoryTable.refresh(allCategories);
            nameFilter.setText("");
        }
    }
}
