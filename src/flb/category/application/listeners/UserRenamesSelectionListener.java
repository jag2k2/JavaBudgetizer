package flb.category.application.listeners;

import flb.category.application.*;
import flb.category.*;
import javax.swing.*;
import java.beans.*;
import java.util.*;

public class UserRenamesSelectionListener implements PropertyChangeListener {
    private final CategoryStorage categoryStorage;
    private final CategoryTable categoryTable;
    private final JTextField nameFilter;
    private String oldName;

    public UserRenamesSelectionListener(CategoryStorage categoryStorage, CategoryTable categoryTable, JTextField nameFilter){
        this.categoryStorage = categoryStorage;
        this.categoryTable = categoryTable;
        this.nameFilter = nameFilter;
        this.oldName = "";
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("tableCellEditor")){
            if(categoryTable.isEditing()){
                oldName = categoryTable.getSelectedRowName();
            }
            else {
                if (categoryTable.getEditingColumn() == 0) {
                    String newName = categoryTable.getSelectedRowName();
                    categoryStorage.renameCategory(oldName, newName);
                    ArrayList<Category> categories = categoryStorage.getCategories(nameFilter.getText());
                    categoryTable.editRefresh(categories);
                }
            }
        }
    }
}
