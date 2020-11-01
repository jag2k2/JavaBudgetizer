package flb.database.interfaces;

import flb.tuples.Category;

import java.util.ArrayList;

public interface CategoryStore {
    void addCategory(String name);
    void deleteCategory(String name);
    void updateAmount(String name, float amount);
    void toggleExclusion(String name);
    void renameCategory(String oldName, String newName);
    ArrayList<Category> getCategories(String nameFilter);
    Boolean categoryExist(String name);
}