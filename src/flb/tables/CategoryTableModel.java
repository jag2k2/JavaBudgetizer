package flb.tables;

import flb.tuples.Category;
import flb.util.Maybe;
import java.util.ArrayList;

public interface CategoryTableModel {
    Maybe<Category> getCategory(int row);
    void updateCategories(ArrayList<Category> tableContents);
}
