package flb.tables.category;

import flb.tuples.Category;
import java.util.ArrayList;

public interface CategoryTableAutomator {
    void setSelectedRow(int row);
    int getSelectedRow();
    ArrayList<Category> getContents();
    void editCellAt(int row, int col);
    void toggleSelectedExcludes();
    void setEditorGoal(float newGoal);
    void setEditorName(String newName);
}
