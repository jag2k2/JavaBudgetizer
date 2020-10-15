package flb.category.application.listeners;

import flb.category.application.*;
import java.awt.event.*;

public class UserAddsCategoryListener implements ActionListener {
    private final CategoryTableEditor tableEditor;

    public UserAddsCategoryListener(CategoryTableEditor tableEditor){
        this.tableEditor = tableEditor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tableEditor.userAddCategory();
    }
}

