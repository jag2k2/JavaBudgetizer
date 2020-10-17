package flb.category.application.listeners;

import flb.category.application.*;
import flb.category.*;
import javax.swing.*;
import java.beans.*;

public class UserRenamesSelectionListener implements PropertyChangeListener, Runnable {
    private final CategoryTableEditor tableEditor;
    private final JTextField nameFilter;
    private String oldName;
    private JTable table;
    private int columnBeingEdited;

    public UserRenamesSelectionListener(CategoryTableEditor tableEditor, JTextField nameFilter) {
        this.tableEditor = tableEditor;
        this.nameFilter = nameFilter;
        this.oldName = "";
        this.columnBeingEdited = -1;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("tableCellEditor")) {
            this.table = (JTable)evt.getSource();
            if (table.isEditing()) {
                for (Category selectedCategory : tableEditor.getSelectedCategory()) {
                    oldName = selectedCategory.getName();
                }
                SwingUtilities.invokeLater(this);
            }
            else if (columnBeingEdited == 0) {
                tableEditor.userRenamedCategory(oldName);
                tableEditor.refreshAndKeepSelection(nameFilter.getText());
            }
        }
    }

    @Override
    public void run()
    {
        columnBeingEdited = table.getEditingColumn();
    }
}
