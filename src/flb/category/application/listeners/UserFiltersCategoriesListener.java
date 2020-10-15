package flb.category.application.listeners;

import flb.category.application.*;
import flb.category.*;

import javax.swing.event.*;
import java.util.*;

public class UserFiltersCategoriesListener implements DocumentListener {
    private final CategoryTableEditor tableEditor;

    public UserFiltersCategoriesListener(CategoryTableEditor tableEditor) {
        this.tableEditor = tableEditor;
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        tableEditor.userFiltersCategories();
    }

    @Override
    public void removeUpdate(DocumentEvent e){
        tableEditor.userFiltersCategories();
    }

}

