package flb.application.category.listeners;

import flb.tuples.Category;
import flb.util.Maybe;

public interface CategoryNameEditor extends EditRefresher {
    Maybe<Category> getSelectedCategory();
    void userRenamedCategory(String oldName);
}