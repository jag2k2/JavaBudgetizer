package flb.components.editors.tables.listeners;

import flb.components.editors.CategoryNameEditor;
import flb.components.monthselector.MonthSelectorImpl;
import flb.tuples.*;
import javax.swing.*;
import java.beans.*;

public class UserRenamesSelectionListener implements PropertyChangeListener {
    private final CategoryNameEditor nameEditor;
    private final JTextField nameFilter;
    private String oldName;
    private MonthSelectorImpl monthSelector;

    public UserRenamesSelectionListener(CategoryNameEditor nameEditor, JTextField nameFilter, MonthSelectorImpl monthSelector) {
        this.nameEditor = nameEditor;
        this.nameFilter = nameFilter;
        this.oldName = "";
        this.monthSelector = monthSelector;
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
                nameEditor.userRenamedCategory(oldName, monthSelector.getSelectedMonth());
                nameEditor.refreshAndKeepSelection(nameFilter.getText());
            }
        }
    }
}
