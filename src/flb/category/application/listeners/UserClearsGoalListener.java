package flb.category.application.listeners;

import flb.category.application.*;

import javax.swing.*;
import java.awt.event.*;

public class UserClearsGoalListener implements ActionListener {
    private final CategoryTableEditor tableEditor;
    private final JTextField nameFilter;

    public UserClearsGoalListener(CategoryTableEditor tableEditor, JTextField nameFilter) {
        this.tableEditor = tableEditor;
        this.nameFilter = nameFilter;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tableEditor.userClearSelectedCategoryGoal();
        tableEditor.refreshAndKeepSelection(nameFilter.getText());

    }
}
