package flb.datastores;

import flb.tuples.Category;
import flb.util.*;
import java.util.ArrayList;

public interface CategoryStore {
    void addCategory(String name);
    void deleteCategory(String name);
    void updateAmount(String name, float amount);
    void toggleExclusion(String name);
    void renameCategory(String oldName, String newName);
    ArrayList<Category> getCategories(String nameFilter);
    boolean categoryExist(String name);
    int getTransactionCountOfCategory(String categoryNameToDelete);
    float getBalance(WhichMonth whichMonth);
}
