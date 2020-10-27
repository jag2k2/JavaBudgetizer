package flb.database;

import java.util.*;
import flb.tuples.Category;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class CategoryStoreEditorTest {
    private CategoryStoreEditor categoryStoreEditor;
    private TestDatabase dataBase;
    ArrayList<Category> expected;
    ArrayList<Category> actual;

    @BeforeEach
    void setUp(){
        expected = new ArrayList<>();
        actual = new ArrayList<>();

        dataBase = new TestDatabase();
        dataBase.connect();
        categoryStoreEditor = new CategoryStoreEditorImp(dataBase);
    }

    @AfterEach
    void tearDown() {
        dataBase.close();
    }

    @Test
    void addCategory() {
        Category categoryToAdd = new Category("Name4", Float.NaN, false);
        expected.add(categoryToAdd);

        categoryStoreEditor.addCategory("Name4");

        actual = categoryStoreEditor.getCategories("Name4");
        assertEquals(expected, actual);
    }

    @Test
    void deleteCategory() {
        expected.add(new Category("Name2", 200, true));
        expected.add(new Category("Name3", 300, false));

        categoryStoreEditor.deleteCategory("Name1");

        actual = categoryStoreEditor.getCategories("Name");
        assertEquals(expected, actual);
    }

    @Test
    void categoryExist() {
        Boolean found = categoryStoreEditor.categoryExist("Name1");
        assertTrue(found);

        found = categoryStoreEditor.categoryExist("NameNeverExist");
        assertFalse(found);
    }

    @Test
    void getCategoriesNoMatch() {
        actual = categoryStoreEditor.getCategories("NameNotExist");
        assertEquals(expected, actual);
    }

    @Test
    void getCategoriesExactMatch() {
        expected.add(new Category("Name1", 100, false));

        ArrayList<Category> actual = categoryStoreEditor.getCategories("Name1");

        assertEquals(expected, actual);
    }

    @Test
    void getCategoriesMultipleMatch() {
        expected.add(new Category("Name1", 100, false));
        expected.add(new Category("Name2", 200, true));
        expected.add(new Category("Name3", 300, false));

        actual = categoryStoreEditor.getCategories("Name");

        assertEquals(expected, actual);
    }

    @Test
    void getAllCategories() {
        expected.add(new Category("Name1", 100, false));
        expected.add(new Category("Name2", 200, true));
        expected.add(new Category("Name3", 300, false));
        expected.add(new Category("Test1", Float.NaN, false));

        actual = categoryStoreEditor.getCategories("");

        assertEquals(expected, actual);
    }

    @Test
    void updateCategoryAmount() {
        expected.add(new Category("Name2", 100, true));

        categoryStoreEditor.updateAmount("Name2", 100);

        actual = categoryStoreEditor.getCategories("Name2");
        assertEquals(expected, actual);
    }

    @Test
    void clearCategoryAmount() {
        expected.add(new Category("Name2", Float.NaN, true));

        categoryStoreEditor.updateAmount("Name2", Float.NaN);

        actual = categoryStoreEditor.getCategories("Name2");
        assertEquals(expected, actual);
    }

    @Test
    void toggleExclusion() {
        expected.add(new Category("Name2", 200, false));

        categoryStoreEditor.toggleExclusion("Name2");

        actual = categoryStoreEditor.getCategories("Name2");
        assertEquals(expected, actual);
    }

    @Test
    void renameCategory() {
        expected.add(new Category("Name5", 200, true));

        categoryStoreEditor.renameCategory("Name2", "Name5");

        actual = categoryStoreEditor.getCategories("Name5");
        assertEquals(expected, actual);
    }
}