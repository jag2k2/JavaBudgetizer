package com.jag2k2.components.editor.category;

import com.jag2k2.components.menus.MenuDisplayer;
import com.jag2k2.tuples.Category;
import com.jag2k2.util.Maybe;
import javax.swing.event.*;
import java.beans.*;
import java.util.ArrayList;

public interface CategoryTable {
    void displayAndClearSelection(ArrayList<Category> tableContents);
    void displayAndKeepSelection(ArrayList<Category> tableContents);
    Maybe<Category> getSelectedCategory();
    Maybe<Category> getCategory(int row);
    void addCategoryRenameListener(PropertyChangeListener propertyChangeListener);
    void addGoalEditListener(TableModelListener tableModelListener);
    void addExcludesEditListener(CellEditorListener cellEditorListener);
    void addEditorMenu (MenuDisplayer menuDisplayer);
}
