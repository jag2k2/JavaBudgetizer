package flb.tables.category.interfaces;

public interface CategoryAdder extends CategoryListChangeRefresher {
    boolean categoryNameAddable(String categoryToAdd);
    void userAddCategory(String categoryToAdd);
}
