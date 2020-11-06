package flb.components.editors.tables;

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
    void addCategoryRenameListener(PropertyChangeListener propertyChangeListener);
    void addGoalEditListener(TableModelListener tableModelListener);
    void addExcludesEditListener(CellEditorListener cellEditorListener);
}
