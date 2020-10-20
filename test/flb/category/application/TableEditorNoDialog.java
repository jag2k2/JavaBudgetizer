package flb.category.application;

import flb.category.*;
import javax.swing.*;

public class TableEditorNoDialog extends CategoryTableEditor {
    private final boolean alwaysConfirm;

    public TableEditorNoDialog(CategoryStorage categoryStorage, CategoryTable categoryTable, boolean alwaysConfirm){
        super(categoryStorage, categoryTable);
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
