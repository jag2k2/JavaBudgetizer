package flb.category.application.listeners;

import flb.category.application.*;
import flb.category.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class UserAddsCategoryListener implements ActionListener {
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
        if(!categoryToAdd.equals("")) {
                if(!categoryStorage.categoryExist(categoryToAdd)) {
                    categoryStorage.addCategory(categoryToAdd);
                    nameFilter.setText("");
                    ArrayList<Category> categories = categoryStorage.getCategories(nameFilter.getText());
                    categoryTable.refresh(categories);

                }
        }
    }
}
