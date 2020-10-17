package flb.category.application.listeners;

import flb.category.application.*;
import flb.category.*;
import javax.swing.*;
import java.beans.*;

public class UserRenamesSelectionListener implements PropertyChangeListener {
    private final CategoryTableEditor tableEditor;
    private final JTextField nameFilter;
    private String oldName;

    public UserRenamesSelectionListener(CategoryTableEditor tableEditor, JTextField nameFilter) {
        this.tableEditor = tableEditor;
        this.nameFilter = nameFilter;
        this.oldName = "";
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("tableCellEditor")) {
            JTable table = (JTable) evt.getSource();
            if (table.isEditing()) {
                for (Category selectedCategory : tableEditor.getSelectedCategory()) {
                    oldName = selectedCategory.getName();
                }
            }
            else if (table.getEditingColumn() == 0) {
                tableEditor.userRenamedCategory(oldName);
                tableEditor.refreshAndKeepSelection(nameFilter.getText());
            }
        }
    }
}
