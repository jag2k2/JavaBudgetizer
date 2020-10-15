package flb.category.application.listeners;

import flb.category.application.*;
import flb.category.*;
import javax.swing.*;
import java.beans.*;
import java.util.*;

public class UserRenamesSelectionListener implements PropertyChangeListener {
    private final CategoryTableEditor tableEditor;
    private String oldName;

    public UserRenamesSelectionListener(CategoryTableEditor tableEditor) {
        this.tableEditor = tableEditor;
        this.oldName = "";
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("tableCellEditor")) {
            if (((JTable) evt.getSource()).getEditingColumn() != 0) {
                ArrayList<Category> maybeCategory = tableEditor.getEditingCategory();
                if (!maybeCategory.isEmpty()){
                    oldName = maybeCategory.get(0).getName();
                }
            }
            else {
                tableEditor.userRenamedCategory(oldName);
            }
        }
    }
}
