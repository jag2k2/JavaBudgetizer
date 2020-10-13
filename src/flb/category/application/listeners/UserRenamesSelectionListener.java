package flb.category.application.listeners;

import flb.category.application.*;
import flb.category.*;
import javax.swing.*;
import java.beans.*;
import java.util.*;

public class UserRenamesSelectionListener implements PropertyChangeListener {
    private final CategoryStorage categoryStorage;
    private final CategoryTable categoryTable;
    private String oldName;

    public UserRenamesSelectionListener(CategoryStorage categoryStorage, CategoryTable categoryTable){
        this.categoryStorage = categoryStorage;
        this.categoryTable = categoryTable;
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
                    ArrayList<Category> categories = categoryStorage.getCategories(categoryTable.getFilterText());
                    categoryTable.editRefresh(categories);
                }
            }
        }
    }
}
