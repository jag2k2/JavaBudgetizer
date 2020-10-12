package flb.category.application.listeners;

import flb.category.*;
import flb.category.application.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.ArrayList;

public class UserEditsExcludesListener implements CellEditorListener {

    private final CategoryStorage categoryStorage;
    private final CategoryTable categoryTable;
    private final JTextField nameFilter;

    public UserEditsExcludesListener(CategoryStorage categoryStorage, JTextField nameFilter, CategoryTable categoryTable){
        this.categoryStorage = categoryStorage;
        this.categoryTable = categoryTable;
        this.nameFilter = nameFilter;
    }

    @Override
    public void editingCanceled(ChangeEvent e) {

    }

    @Override
    public void editingStopped(ChangeEvent e) {
        String selectedName = categoryTable.getSelectedRowName();
        categoryStorage.toggleExclusion(selectedName);
        ArrayList<Category> categories = categoryStorage.getCategories(nameFilter.getText());
        categoryTable.editRefresh(categories);
    }
}
