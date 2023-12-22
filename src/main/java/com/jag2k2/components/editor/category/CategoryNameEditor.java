package com.jag2k2.components.editor.category;

import com.jag2k2.tuples.Category;
import com.jag2k2.util.Maybe;

public interface CategoryNameEditor {
    Maybe<Category> getSelectedCategory();
    void renamedCategory(String oldName);
}
