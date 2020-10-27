package flb.application.category.listeners;

public interface CategoryAdder extends ListChangeRefresher {
    boolean categoryNameAddable(String categoryToAdd);
    void userAddCategory(String categoryToAdd);
}
