package flb.components.editors;

public interface CategoryAdder extends CategoryListChangeRefresher {
    boolean categoryNameAddable(String categoryToAdd);
    void userAddCategory(String categoryToAdd);
}
