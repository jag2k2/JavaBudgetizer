package flb.category.application.listeners;

import flb.category.application.*;
import java.awt.event.*;

public class UserClearsGoalListener implements ActionListener {
    private final CategoryTableEditor tableEditor;

    public UserClearsGoalListener(CategoryTableEditor tableEditor) {
        this.tableEditor = tableEditor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tableEditor.userClearCategoryGoal();
    }
}
