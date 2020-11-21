package flb.listeners;

import flb.components.editors.CategoryNameEditor;
import flb.tuples.*;
import javax.swing.*;
import java.beans.*;

public class UserRenamesCategoryListener implements PropertyChangeListener {
    private final CategoryNameEditor nameEditor;
    private final JTextField nameFilter;
    private String oldName;

    public UserRenamesCategoryListener(CategoryNameEditor nameEditor, JTextField nameFilter) {
        this.nameEditor = nameEditor;
        this.nameFilter = nameFilter;
        this.oldName = "";
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("tableCellEditor")) {
            JTable table = (JTable) evt.getSource();
            if (table.isEditing()) {
                for (Category selectedCategory : nameEditor.getSelectedCategory()) {
                    oldName = selectedCategory.getName();
                }
            }
            else if (table.getEditingColumn() == 0) {
                nameEditor.userRenamedCategory(oldName);
                nameEditor.refreshAndKeepSelection(nameFilter.getText());
            }
        }
    }
}