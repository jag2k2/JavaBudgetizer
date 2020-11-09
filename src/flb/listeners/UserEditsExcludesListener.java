package flb.listeners;

import flb.components.editors.CategoryExcludeEditor;

import javax.swing.*;
import javax.swing.event.*;

public class UserEditsExcludesListener implements CellEditorListener {
    private final CategoryExcludeEditor excludeEditor;
    private final JTextField nameFilter;

    public UserEditsExcludesListener(CategoryExcludeEditor excludeEditor, JTextField nameFilter){
        this.excludeEditor = excludeEditor;
        this.nameFilter = nameFilter;
    }

    @Override
    public void editingCanceled(ChangeEvent e) {
    }

    @Override
    public void editingStopped(ChangeEvent e) {
        excludeEditor.userEditsSelectedExcludes();
        excludeEditor.refreshAndKeepSelection(nameFilter.getText());
    }
}
