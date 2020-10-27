package flb.tables;

import flb.database.CategoryStoreEditor;
import javax.swing.*;

public class TableEditorNoDialog extends CategoryTableEditorImp {
    private final boolean alwaysConfirm;

    public TableEditorNoDialog(CategoryStoreEditor categoryStoreEditor, CategoryTable categoryTable, boolean alwaysConfirm){
        super(categoryStoreEditor, categoryTable);
        this.alwaysConfirm = alwaysConfirm;
    }

    @Override
    protected int getSelectionFromDialog(String categoryNameToDelete, JFrame frame){
        if(alwaysConfirm) {
            return JOptionPane.YES_OPTION;
        }
        else {
            return JOptionPane.NO_OPTION;
        }
    }
}
