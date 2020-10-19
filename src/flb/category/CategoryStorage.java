package flb.category;

import java.util.ArrayList;

public interface CategoryStorage {
    void addCategory(String name);
    void deleteCategory(String name);
    void updateAmount(String name, float amount);
    void toggleExclusion(String name);
    void renameCategory(String oldName, String newName);
    ArrayList<Category> getCategories(String nameFilter);
    Boolean categoryExist(String name);
}
