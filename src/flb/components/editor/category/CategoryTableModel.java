package flb.components.editor.category;

import flb.tuples.Category;
import flb.util.Maybe;
import javax.swing.event.TableModelListener;
import java.util.ArrayList;

public interface CategoryTableModel {
    Maybe<Category> getCategory(int row);
    void updateCategories(ArrayList<Category> tableContents);
    void addTableModelListener(TableModelListener tableModelListener);
    ArrayList<Category> getCategories();
}
