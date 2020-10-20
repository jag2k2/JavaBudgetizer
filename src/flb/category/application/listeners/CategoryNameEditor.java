package flb.category.application.listeners;

import flb.category.Category;
import flb.util.Maybe;

public interface CategoryNameEditor extends EditRefresher {
    Maybe<Category> getSelectedCategory();
    void userRenamedCategory(String oldName);
}
