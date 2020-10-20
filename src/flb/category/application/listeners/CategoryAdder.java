package flb.category.application.listeners;

public interface CategoryAdder extends ListChangeRefresher {
    boolean categoryNameAddable(String categoryToAdd);
    void userAddCategory(String categoryToAdd);
}
