package flb.category.application.listeners;

import flb.category.application.*;

import javax.swing.*;
import javax.swing.event.*;

public class UserEditsExcludesListener implements CellEditorListener {
    private final CategoryTableEditor tableEditor;
    private final JTextField nameFilter;

    public UserEditsExcludesListener(CategoryTableEditor tableEditor, JTextField nameFilter){
        this.tableEditor = tableEditor;
        this.nameFilter = nameFilter;
    }

    @Override
    public void editingCanceled(ChangeEvent e) {
    }

    @Override
    public void editingStopped(ChangeEvent e) {
        tableEditor.userEditsSelectedExcludes();
        tableEditor.refreshAndKeepSelection(nameFilter.getText());
    }
}
