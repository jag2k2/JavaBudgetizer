package flb.category.management;

import flb.category.CategoryStorage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class UserDeletesCategoryListener implements ActionListener {

    private final CategoryStorage categoryStorage;
    private final CategoryTableModel tableModel;
    private final JTable table;

    public UserDeletesCategoryListener(CategoryStorage categoryStorage, CategoryTableModel tableModel, JTable table){
        this.categoryStorage = categoryStorage;
        this.tableModel = tableModel;
        this.table = table;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        String categoryNameToDelete = tableModel.getRowName(selectedRow);
        categoryStorage.deleteCategory(categoryNameToDelete);
        tableModel.setContents(categoryStorage.getCategories(""));
        tableModel.fireTableDataChanged();
    }
}
