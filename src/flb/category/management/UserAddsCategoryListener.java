package flb.category.management;

import flb.category.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class UserAddsCategoryListener implements ActionListener {
    private final CategoryStorage categoryStorage;
    private final CategoryTable categoryTable;
    private final JTextField nameFilterField;

    public UserAddsCategoryListener(CategoryStorage categoryStorage, JTextField nameFilterField, CategoryTable categoryTable){
        this.categoryStorage = categoryStorage;
        this.categoryTable = categoryTable;
        this.nameFilterField = nameFilterField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        categoryStorage.addCategory(nameFilterField.getText());
        categoryTable.refresh(categoryStorage.getCategories(""));
    }
}
