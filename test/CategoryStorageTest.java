import java.util.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class CategoryStorageTest {
    private final static String testTable = "categories";
    private final static String databaseName = "test";
    private CategoryStorage categoryStorage;
    private Database database;
    ArrayList<Category> expected;
    ArrayList<Category> actual;

    @BeforeEach
    void init(){
        expected = new ArrayList<>();
        actual = new ArrayList<>();
        database = new Database(databaseName);
        categoryStorage = new CategoryStorage(database);

        String update = "CREATE TABLE test." + testTable +
                " ( `id` INT NOT NULL AUTO_INCREMENT, " +
                "`name` VARCHAR(255) NOT NULL , " +
                "`default_goal_amt` FLOAT(9.2) NULL DEFAULT NULL , " +
                "`exclude` BOOLEAN NOT NULL , " +
                "PRIMARY KEY (`id`)) ENGINE = InnoDB";
        database.executeUpdate(update);

        update = "INSERT INTO " + testTable + " (name, default_goal_amt, exclude) VALUES " +
                "('Name1', '100', FALSE), " +
                "('Name2', '200', TRUE), " +
                "('Name3', '300', FALSE), " +
                "('Test1', NULL, FALSE)";
        database.executeUpdate(update);
    }

    @Test
    void addCategory() {
        Category categoryToAdd = new Category("Name4", Float.NaN, false);
        expected.add(categoryToAdd);

        categoryStorage.addCategory("Name4");

        actual = categoryStorage.getCategories("Name4");
        assertEquals(expected, actual);
    }

    @Test
    void deleteCategory() {
        expected.add(new Category("Name2", 200, true));
        expected.add(new Category("Name3", 300, false));

        categoryStorage.deleteCategory("Name1");

        actual = categoryStorage.getCategories("Name");
        assertEquals(expected, actual);
    }

    @Test
    void categoryExist() {
        Boolean found = categoryStorage.categoryExist("Name1");
        assertTrue(found);

        found = categoryStorage.categoryExist("NameNeverExist");
        assertFalse(found);
    }

    @Test
    void getCategoriesNoMatch() {
        actual = categoryStorage.getCategories("NameNotExist");
        assertEquals(expected, actual);
    }

    @Test
    void getCategoriesExactMatch() {
        expected.add(new Category("Name1", 100, false));

        ArrayList<Category> actual = categoryStorage.getCategories("Name1");

        assertEquals(expected, actual);
    }

    @Test
    void getCategoriesMultipleMatch() {
        expected.add(new Category("Name1", 100, false));
        expected.add(new Category("Name2", 200, true));
        expected.add(new Category("Name3", 300, false));

        actual = categoryStorage.getCategories("Name");

        assertEquals(expected, actual);
    }

    @Test
    void getAllCategories() {
        expected.add(new Category("Name1", 100, false));
        expected.add(new Category("Name2", 200, true));
        expected.add(new Category("Name3", 300, false));
        expected.add(new Category("Test1", Float.NaN, false));

        actual = categoryStorage.getCategories("");

        assertEquals(expected, actual);
    }

    @Test
    void updateCategoryAmount() {
        expected.add(new Category("Name2", 100, true));

        categoryStorage.updateAmount("Name2", 100);

        actual = categoryStorage.getCategories("Name2");
        assertEquals(expected, actual);
    }

    @Test
    void clearCategoryAmount() {
        expected.add(new Category("Name2", Float.NaN, true));

        categoryStorage.updateAmount("Name2", Float.NaN);

        actual = categoryStorage.getCategories("Name2");
        assertEquals(expected, actual);
    }

    @Test
    void toggleExclusion() {
        expected.add(new Category("Name2", 200, false));

        categoryStorage.toggleExclusion("Name2");

        actual = categoryStorage.getCategories("Name2");
        assertEquals(expected, actual);
    }

    @Test
    void renameCategory() {
        expected.add(new Category("Name5", 200, true));

        categoryStorage.renameCategory("Name2", "Name5");

        actual = categoryStorage.getCategories("Name5");
        assertEquals(expected, actual);
    }

    @AfterEach
    void dropTable() {
        database.executeUpdate("DROP TABLE " + testTable);
        database.close();
    }
}