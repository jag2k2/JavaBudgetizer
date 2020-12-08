package flb.listeners;

import flb.components.editor.category.CategoryNameEditor;
import flb.tuples.*;
import javax.swing.*;
import java.beans.*;

public class UserRenamesCategoryListener implements PropertyChangeListener {
    private final CategoryNameEditor nameEditor;
    private String oldName;

    public UserRenamesCategoryListener(CategoryNameEditor nameEditor) {
        this.nameEditor = nameEditor;
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
                nameEditor.renamedCategory(oldName);
            }
        }
    }
}
