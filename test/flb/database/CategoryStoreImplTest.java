package flb.database;

import java.util.*;

import flb.database.interfaces.CategoryStore;
import flb.tuples.Category;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class CategoryStoreImplTest {
    private TestDatabase dataBase;
    private CategoryStore categoryStore;
    ArrayList<Category> expected;
    ArrayList<Category> actual;

    @BeforeEach
    void setUp(){
        expected = new ArrayList<>();
        actual = new ArrayList<>();

        dataBase = new TestDatabase();
        dataBase.connect();
        categoryStore = new CategoryStoreImpl(dataBase);
    }

    @AfterEach
    void tearDown() {
        dataBase.close();
    }

    @Test
    void addCategory() {
        Category categoryToAdd = new Category("Name4", Float.NaN, false);
        expected.add(categoryToAdd);

        categoryStore.addCategory("Name4");

        actual = categoryStore.getCategories("Name4");
        assertEquals(expected, actual);
    }

    @Test
    void deleteCategory() {
        expected.add(new Category("Name2", 200, true));
        expected.add(new Category("Name3", 300, false));

        categoryStore.deleteCategory("Name1");

        actual = categoryStore.getCategories("Name");
        assertEquals(expected, actual);
    }

    @Test
    void categoryExist() {
        Boolean found = categoryStore.categoryExist("Name1");
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
        expected.add(new Category("Name1", 100, false));

        ArrayList<Category> actual = categoryStore.getCategories("Name1");

        assertEquals(expected, actual);
    }

    @Test
    void getCategoriesMultipleMatch() {
        expected.add(new Category("Name1", 100, false));
        expected.add(new Category("Name2", 200, true));
        expected.add(new Category("Name3", 300, false));

        actual = categoryStore.getCategories("Name");

        assertEquals(expected, actual);
    }

    @Test
    void getAllCategories() {
        expected.add(new Category("Name1", 100, false));
        expected.add(new Category("Name2", 200, true));
        expected.add(new Category("Name3", 300, false));
        expected.add(new Category("Test1::sub1", Float.NaN, false));
        expected.add(new Category("Test1::sub2", 500, true));

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
        expected.add(new Category("Name2", Float.NaN, true));

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
}