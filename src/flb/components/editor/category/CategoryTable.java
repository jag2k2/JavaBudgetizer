package flb.components.editor.category;

import flb.components.menus.MenuDisplayer;
import flb.tuples.Category;
import flb.util.Maybe;
import javax.swing.*;
import javax.swing.event.*;
import java.beans.*;
import java.util.ArrayList;

public interface CategoryTable {
    JScrollPane getPane();
    void displayAndClearSelection(ArrayList<Category> tableContents);
    void displayAndKeepSelection(ArrayList<Category> tableContents);
    Maybe<Category> getSelectedCategory();
    Maybe<Category> getCategory(int row);
    void addCategoryRenameListener(PropertyChangeListener propertyChangeListener);
    void addGoalEditListener(TableModelListener tableModelListener);
    void addExcludesEditListener(CellEditorListener cellEditorListener);
    void addEditorMenu (MenuDisplayer menuDisplayer);
}
