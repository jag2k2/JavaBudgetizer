package flb.components.editor.category;

import flb.tuples.Category;
import flb.util.Maybe;

public interface CategoryNameEditor {
    Maybe<Category> getSelectedCategory();
    void renamedCategory(String oldName);
}
