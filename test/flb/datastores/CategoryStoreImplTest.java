package flb.datastores;

import java.util.*;

import flb.tuples.Category;
import flb.util.WhichMonth;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class CategoryStoreImplTest {
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
        categoryStore = new CategoryStoreImpl(database);
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void addCategory() {
        Category categoryToAdd = new Category("Name4", false);
        expected.add(categoryToAdd);

        categoryStore.addCategory("Name4");

        actual = categoryStore.getCategories("Name4");
        assertEquals(expected, actual);
    }

    @Test
    void countTransactions() {
        String categoryName = TestDatabase.getTestCategories().get(1).getName();
        int actual = categoryStore.getTransactionCountOfCategory(categoryName);
        assertEquals(1, actual);
    }

    @Test
    void deleteCategory() {
        Category categoryToDelete = TestDatabase.getTestCategories().get(1);
        expected.add(TestDatabase.getTestCategories().get(2));

        categoryStore.deleteCategory(categoryToDelete.getName());

        actual = categoryStore.getCategories("Name");
        assertEquals(expected, actual);
    }

    @Test
    void categoryExist() {
        String realName = TestDatabase.getTestCategories().get(0).getName();
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
        Category category = TestDatabase.getTestCategories().get(0);
        expected.add(category);

        ArrayList<Category> actual = categoryStore.getCategories(category.getName());

        assertEquals(expected, actual);
    }

    @Test
    void getCategoriesMultipleMatch() {
        String nameFilter = "Name";
        for(Category category : TestDatabase.getTestCategories()){
            if(category.getName().contains(nameFilter)){
                expected.add(category);
            }
        }

        actual = categoryStore.getCategories(nameFilter);

        assertEquals(expected, actual);
    }

    @Test
    void getAllCategories() {
        expected = TestDatabase.getTestCategories();

        actual = categoryStore.getCategories("");

        assertEquals(expected, actual);
    }

    @Test
    void updateCategoryAmount() {
        expected.add(new Category("Name2", 100, true));

        categoryStore.updateAmount("Name2", 100);

        actual = categoryStore.getCategories("Name2");
        assertEquals(expected, actual);
    }

    @Test
    void clearCategoryAmount() {
        expected.add(new Category("Name2", true));

        categoryStore.updateAmount("Name2", Float.NaN);

        actual = categoryStore.getCategories("Name2");
        assertEquals(expected, actual);
    }

    @Test
    void toggleExclusion() {
        expected.add(new Category("Name2", 200, false));

        categoryStore.toggleExclusion("Name2");

        actual = categoryStore.getCategories("Name2");
        assertEquals(expected, actual);
    }

    @Test
    void renameCategory() {
        expected.add(new Category("Name5", 200, true));

        categoryStore.renameCategory("Name2", "Name5");

        actual = categoryStore.getCategories("Name5");
        assertEquals(expected, actual);
    }

    @Test
    void getBalance() {
        System.out.println(categoryStore.getBalance(new WhichMonth(2020, Calendar.OCTOBER)));
    }
}