package flb.category.application.listeners;

import flb.category.application.*;
import javax.swing.*;
import javax.swing.event.*;

public class UserFiltersCategoriesListener implements DocumentListener {
    private final CategoryTableEditor tableEditor;
    private final JTextField nameFilter;

    public UserFiltersCategoriesListener(CategoryTableEditor tableEditor, JTextField nameFilter) {
        this.tableEditor = tableEditor;
        this.nameFilter = nameFilter;
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        System.out.println("insert");
        tableEditor.refresh(nameFilter.getText());
    }

    @Override
    public void removeUpdate(DocumentEvent e){
        System.out.println("remove");
        tableEditor.refresh(nameFilter.getText());
    }

}

