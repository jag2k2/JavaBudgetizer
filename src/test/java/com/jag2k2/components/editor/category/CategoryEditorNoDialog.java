package com.jag2k2.components.editor.category;

import com.jag2k2.datastores.CategoryStore;
import javax.swing.*;
import java.awt.*;

public class CategoryEditorNoDialog extends CategoryEditorImpl {
    private final boolean alwaysConfirm;

    public CategoryEditorNoDialog(CategoryStore categoryStore, boolean alwaysConfirm){
        super(categoryStore);
        this.alwaysConfirm = alwaysConfirm;
    }

    @Override
    protected int getConfirmationFromDialog(String categoryNameToDelete, Component component){
        if(alwaysConfirm) {
            return JOptionPane.YES_OPTION;
        }
        else {
            return JOptionPane.NO_OPTION;
        }
    }

    @Override
    protected void notifyUserWhyWontDelete(String categoryNameToDelete, int transactionCount, Component component){}
}
