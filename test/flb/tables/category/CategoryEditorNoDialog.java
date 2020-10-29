package flb.tables.category;

import flb.database.*;
import javax.swing.*;

public class CategoryEditorNoDialog extends CategoryEditorImp {
    private final boolean alwaysConfirm;

    public CategoryEditorNoDialog(CategoryStore categoryStore, CategoryTable categoryTable, boolean alwaysConfirm){
        super(categoryStore, categoryTable);
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
