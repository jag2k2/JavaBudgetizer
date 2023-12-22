package com.jag2k2.components.editor.category;

import com.jag2k2.tuples.Category;
import com.jag2k2.util.Maybe;
import javax.swing.event.TableModelListener;
import java.util.ArrayList;

public interface CategoryTableModel {
    Maybe<Category> getCategory(int row);
    void updateCategories(ArrayList<Category> tableContents);
    void addTableModelListener(TableModelListener tableModelListener);
    ArrayList<Category> getCategories();
}
