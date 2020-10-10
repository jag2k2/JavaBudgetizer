package flb.category.management;

import flb.category.CategoryStorage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class UserDeletesCategoryListener implements ActionListener {

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
        categoryTable.refresh(categoryStorage.getCategories(""));
    }
}
