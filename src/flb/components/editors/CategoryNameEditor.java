package flb.components.editors;

import flb.tuples.Category;
import flb.util.Maybe;
import flb.util.WhichMonth;

public interface CategoryNameEditor extends CategoryEditRefresher {
    Maybe<Category> getSelectedCategory();
    void userRenamedCategory(String oldName);
}
