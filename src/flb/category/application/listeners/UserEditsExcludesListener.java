package flb.category.application.listeners;

import flb.category.*;
import flb.category.application.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.ArrayList;

public class UserEditsExcludesListener implements CellEditorListener {

    private final CategoryStorage categoryStorage;
    private final CategoryTable categoryTable;

    public UserEditsExcludesListener(CategoryStorage categoryStorage, CategoryTable categoryTable){
        this.categoryStorage = categoryStorage;
        this.categoryTable = categoryTable;
    }

    @Override
    public void editingCanceled(ChangeEvent e) {

    }

    @Override
    public void editingStopped(ChangeEvent e) {
        String selectedName = categoryTable.getSelectedRowName();
        categoryStorage.toggleExclusion(selectedName);
        ArrayList<Category> categories = categoryStorage.getCategories(categoryTable.getFilterText());
        categoryTable.editRefresh(categories);
    }
}
