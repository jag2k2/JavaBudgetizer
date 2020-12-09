package flb.datastores;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import flb.databases.*;
import flb.tuples.Category;
import flb.util.WhichMonth;
import java.util.*;

class CategoryStoreTest {
    private TestDatabase database;
    private CategoryStore categoryStore;
    List<Category> expected;
    List<Category> actual;

    @BeforeEach
    void setUp(){
        expected = new ArrayList<>();
        actual = new ArrayList<>();

        database = new TestDatabase();
        database.connect();
        categoryStore = new DataStoreImpl(database);
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void addCategory() {
        String newName = "Name4";

        categoryStore.addCategory(newName);

        expected.add(CategoryFactory.makeIncludedCategory(newName));
        assertEquals(expected, categoryStore.getCategories(newName));
    }

    @Test
    void countTransactions() {
        String categoryName = CategoryListFactory.makeDefaultCategories().get(1).getName();
        int actual = categoryStore.getTransactionCountOfCategory(categoryName);
        assertEquals(1, actual);
    }

    @Test
    void deleteCategory() {
        Category categoryToDelete = CategoryListFactory.makeDefaultCategories().get(1);
        expected.add(CategoryListFactory.makeDefaultCategories().get(2));

        categoryStore.deleteCategory(categoryToDelete.getName());

        actual = categoryStore.getCategories("Name");
        assertEquals(expected, actual);
    }

    @Test
    void categoryExist() {
        String realName = CategoryListFactory.makeDefaultCategories().get(0).getName();
        boolean found = categoryStore.categoryExist(realName);
        assertTrue(found);

        found = categoryStore.categoryExist("NameNeverExist");
        assertFalse(found);
    }

    @Test
    void getCategoriesNoMatch() {
        actual = categoryStore.getCategories("NameNotExist");
        assertEquals(expected, actual);
    }

    @Test
    void getCategoriesExactMatch() {
        Category category = CategoryListFactory.makeDefaultCategories().get(0);
        expected.add(category);

        ArrayList<Category> actual = categoryStore.getCategories(category.getName());

        assertEquals(expected, actual);
    }

    @Test
    void getCategoriesMultipleMatch() {
        String nameFilter = "Name";
        for(Category category : CategoryListFactory.makeDefaultCategories()){
            if(category.getName().contains(nameFilter)){
                expected.add(category);
            }
        }

        actual = categoryStore.getCategories(nameFilter);

        assertEquals(expected, actual);
    }

    @Test
    void getAllCategories() {
        expected = CategoryListFactory.makeDefaultCategories();

        actual = categoryStore.getCategories("");

        assertEquals(expected, actual);
    }

    @Test
    void updateCategoryAmount() {
        String categoryToUpdate = "Name2";
        float newAmount = 100;

        categoryStore.updateAmount(categoryToUpdate, newAmount);

        expected = CategoryListFactory.makeDefaultCategoriesWithOneAmountChanged(categoryToUpdate, newAmount);
        assertEquals(expected, categoryStore.getCategories(""));
    }

    @Test
    void clearCategoryAmount() {
        String categoryToUpdate = "Name2";

        categoryStore.updateAmount(categoryToUpdate, Float.NaN);

        expected = CategoryListFactory.makeDefaultCategoriesWithOneCleared(categoryToUpdate);
        assertEquals(expected, categoryStore.getCategories(""));
    }

    @Test
    void toggleExclusion() {
        String selectedCategory = "Name2";

        categoryStore.toggleExclusion(selectedCategory);

        expected = CategoryListFactory.makeDefaultCategoriesWithOneExcludesChanged(selectedCategory, false);
        assertEquals(expected, categoryStore.getCategories(""));
    }

    @Test
    void renameCategory() {
        String oldName = "Name2";
        String newName = "Name20";

        categoryStore.renameCategory(oldName, newName);

        expected = CategoryListFactory.makeDefaultCategoriesWithOneRenamed(oldName, newName);
        assertEquals(expected, categoryStore.getCategories(""));
    }

    @Test
    void getBalance() {
        assertEquals(930F, categoryStore.getBalance(new WhichMonth(2020, Calendar.OCTOBER)));
    }
}