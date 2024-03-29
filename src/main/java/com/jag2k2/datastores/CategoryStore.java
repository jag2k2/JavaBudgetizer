package com.jag2k2.datastores;

import com.jag2k2.tuples.Category;
import java.util.ArrayList;

public interface CategoryStore extends StoreChangeNotifier{
    void addCategory(String name);
    void deleteCategory(String name);
    void updateAmount(String name, float amount);
    void toggleExclusion(String name);
    void renameCategory(String oldName, String newName);
    ArrayList<Category> getCategories(String nameFilter);
    boolean categoryExist(String name);
    int getTransactionCountOfCategory(String categoryNameToDelete);
}
