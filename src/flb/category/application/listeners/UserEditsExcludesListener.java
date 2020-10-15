package flb.category.application.listeners;

import flb.category.application.*;
import javax.swing.event.*;

public class UserEditsExcludesListener implements CellEditorListener {
    private final CategoryTableEditor tableEditor;

    public UserEditsExcludesListener(CategoryTableEditor tableEditor){
        this.tableEditor = tableEditor;
    }

    @Override
    public void editingCanceled(ChangeEvent e) {
    }

    @Override
    public void editingStopped(ChangeEvent e) {
        tableEditor.userEditsExcludes();
    }
}
