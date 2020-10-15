package flb.category.application.listeners;

import flb.category.application.*;
import java.awt.event.*;
import javax.swing.*;


public class UserDeletesCategoryListener implements ActionListener {
    private final CategoryTableEditor tableEditor;
    private final JFrame frame;

    public UserDeletesCategoryListener(CategoryTableEditor tableEditor, JFrame frame){
        this.tableEditor = tableEditor;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tableEditor.userDeleteCategory(frame);
    }
}
