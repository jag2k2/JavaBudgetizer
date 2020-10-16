package flb.category.application.listeners;

import flb.category.application.*;

import javax.swing.*;
import java.awt.event.*;

public class UserAddsCategoryListener implements ActionListener {
    private final CategoryTableEditor tableEditor;
    private final JTextField nameFilter;

    public UserAddsCategoryListener(CategoryTableEditor tableEditor, JTextField nameFilter){
        this.tableEditor = tableEditor;
        this.nameFilter = nameFilter;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nameToAdd = nameFilter.getText();
        if (tableEditor.categoryNameAddable(nameToAdd)) {
            tableEditor.userAddCategory(nameToAdd);
            nameFilter.setText("");
            tableEditor.refresh("");
        }
    }
}

