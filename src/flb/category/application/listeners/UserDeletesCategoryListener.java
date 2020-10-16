package flb.category.application.listeners;

import flb.category.application.*;
import java.awt.event.*;
import javax.swing.*;


public class UserDeletesCategoryListener implements ActionListener {
    private final CategoryTableEditor tableEditor;
    private final JTextField nameFilter;
    private final JFrame frame;

    public UserDeletesCategoryListener(CategoryTableEditor tableEditor, JTextField nameFilter, JFrame frame){
        this.tableEditor = tableEditor;
        this.nameFilter = nameFilter;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tableEditor.userDeleteSelectedCategory(frame);
        tableEditor.refresh(nameFilter.getText());
    }
}
