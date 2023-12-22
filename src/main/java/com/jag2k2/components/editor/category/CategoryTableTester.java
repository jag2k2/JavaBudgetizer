package com.jag2k2.components.editor.category;

import com.jag2k2.tuples.Category;
import java.util.ArrayList;

public interface CategoryTableTester {
    void setSelectedRow(int row);
    int getSelectedRow();
    ArrayList<Category> getContents();
    void editCellAt(int row, int col);
    void toggleSelectedExcludes();
    void setEditorGoal(float newGoal);
    void setEditorName(String newName);
}
