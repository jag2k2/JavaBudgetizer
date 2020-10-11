package flb.category.management;

import flb.category.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

class UserDeletesCategoryListener implements ActionListener {

    private final CategoryStorage categoryStorage;
    private final CategoryTable categoryTable;
    private final JTextField nameFilter;


    public UserDeletesCategoryListener(CategoryStorage categoryStorage, JTextField nameFilter, CategoryTable categoryTable){
        this.categoryStorage = categoryStorage;
        this.nameFilter = nameFilter;
        this.categoryTable = categoryTable;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String categoryNameToDelete = categoryTable.getSelectedRowName();
        categoryStorage.deleteCategory(categoryNameToDelete);
        ArrayList<Category> allCategories = categoryStorage.getCategories("");
        categoryTable.refresh(allCategories);
        nameFilter.setText("");
    }
}
