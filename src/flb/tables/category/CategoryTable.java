package flb.tables.category;

import flb.tuples.Category;
import flb.util.Maybe;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.beans.*;
import java.util.ArrayList;

public interface CategoryTable {
    Maybe<Category> getSelectedCategory();
    void displayAndClearSelection(ArrayList<Category> tableContents);
    void displayAndKeepSelection(ArrayList<Category> tableContents);
    void addCategoryRenameListener(PropertyChangeListener propertyChangeListener);
    void addGoalEditListener(TableModelListener tableModelListener);
    void addExcludesEditListener(CellEditorListener cellEditorListener);
    JScrollPane getPane();
    void setSelectedRow(int row);
    int getSelectedRow();
    ArrayList<Category> getContents();
    void editCellAt(int row, int col);
    void toggleSelectedExcludes();
    void setEditorGoal(float newGoal);
    void setEditorName(String newName);
}
