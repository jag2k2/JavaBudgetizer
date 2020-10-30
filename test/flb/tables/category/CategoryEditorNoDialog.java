package flb.tables.category;

import flb.database.interfaces.CategoryStore;

import javax.swing.*;

public class CategoryEditorNoDialog extends CategoryEditorImpl {
    private final boolean alwaysConfirm;

    public CategoryEditorNoDialog(CategoryStore categoryStore, boolean alwaysConfirm){
        super(categoryStore);
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
