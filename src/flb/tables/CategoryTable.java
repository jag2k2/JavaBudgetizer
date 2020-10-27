package flb.tables;

import flb.tuples.Category;
import flb.util.Maybe;

import java.util.ArrayList;

public interface CategoryTable {
    Maybe<Category> getSelectedCategory();
    void displayAndClearSelection(ArrayList<Category> tableContents);
    void displayAndKeepSelection(ArrayList<Category> tableContents);
}
