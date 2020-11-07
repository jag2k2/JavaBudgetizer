package flb.components.editors.tables.listeners;

import flb.components.editors.CategoryListChangeRefresher;
import javax.swing.*;
import javax.swing.event.*;

public class UserFiltersCategoriesListener implements DocumentListener {
    private final CategoryListChangeRefresher changeRefresher;
    private final JTextField nameFilter;

    public UserFiltersCategoriesListener(CategoryListChangeRefresher changeRefresher, JTextField nameFilter) {
        this.changeRefresher = changeRefresher;
        this.nameFilter = nameFilter;
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        changeRefresher.refreshAndClearSelection(nameFilter.getText());
    }

    @Override
    public void removeUpdate(DocumentEvent e){
        changeRefresher.refreshAndClearSelection(nameFilter.getText());
    }
}

