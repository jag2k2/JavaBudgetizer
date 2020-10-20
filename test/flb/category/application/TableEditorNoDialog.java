package flb.category.application;

import flb.category.*;
import javax.swing.*;

public class TableEditorNoDialog extends CategoryTableEditorImp {
    private final boolean alwaysConfirm;

    public TableEditorNoDialog(CategoryStoreEditor categoryStoreEditor, CategoryTableImp categoryTableImp, boolean alwaysConfirm){
        super(categoryStoreEditor, categoryTableImp);
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
