package com.jag2k2.listeners;

import com.jag2k2.components.editor.category.CategoryExcludeEditor;

import javax.swing.event.*;

public class UserEditsExcludesListener implements CellEditorListener {
    private final CategoryExcludeEditor excludeEditor;

    public UserEditsExcludesListener(CategoryExcludeEditor excludeEditor){
        this.excludeEditor = excludeEditor;
    }

    @Override
    public void editingCanceled(ChangeEvent e) {
    }

    @Override
    public void editingStopped(ChangeEvent e) {
        excludeEditor.editsSelectedExcludes();
    }
}
