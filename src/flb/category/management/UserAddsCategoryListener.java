package flb.category.management;

import flb.category.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class UserAddsCategoryListener implements ActionListener {
    private final CategoryStorage categoryStorage;
    private final CategoryTableModel tableModel;
    private final JTextField nameFilterField;

    public UserAddsCategoryListener(CategoryStorage categoryStorage, JTextField nameFilterField, CategoryTableModel tableModel){
        this.categoryStorage = categoryStorage;
        this.tableModel = tableModel;
        this.nameFilterField = nameFilterField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        categoryStorage.addCategory(nameFilterField.getText());
        tableModel.setContents(categoryStorage.getCategories(""));
        tableModel.fireTableDataChanged();
    }
}
