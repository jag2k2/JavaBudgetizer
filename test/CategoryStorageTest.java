import java.util.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class CategoryStorageTest {

    private final static String testTable = "categories";
    private final static String databaseName = "test";
    private CategoryStorage categoryStorage;
    private DatabaseConnection connection;

    @BeforeEach
    void init(){
        categoryStorage = new CategoryStorage(databaseName);
        connection = new DatabaseConnection(databaseName);

        String update = "CREATE TABLE test." + testTable +
                " ( `id` INT NOT NULL AUTO_INCREMENT, " +
                "`name` VARCHAR(255) NOT NULL , " +
                "`default_goal_amt` FLOAT(9.2) NULL DEFAULT NULL , " +
                "`exclude` BOOLEAN NOT NULL , " +
                "PRIMARY KEY (`id`)) ENGINE = InnoDB";
        connection.executeUpdate(update);

        update = "INSERT INTO " + testTable + " (name, default_goal_amt, exclude) VALUES " +
                "('Name1', '100', FALSE), " +
                "('Name2', '200', TRUE), " +
                "('Name3', '300', FALSE), " +
                "('Test1', NULL, FALSE)";
        connection.executeUpdate(update);
    }

    @Test
    void addCategory() {
        Category categoryToAdd = new Category("Name4", Float.NaN, false);
        ArrayList<Category> expected = new ArrayList<>();
        expected.add(categoryToAdd);

        categoryStorage.addCategory("Name4");
        ArrayList<Category> actual = categoryStorage.getCategories("Name4");

        assertEquals(expected, actual);
    }

    @Test
    void deleteCategory() {
        ArrayList<Category> expected = new ArrayList<>();
        expected.add(new Category("Name2", 200, true));
        expected.add(new Category("Name3", 300, false));

        categoryStorage.deleteCategory("Name1");

        ArrayList<Category> actual = categoryStorage.getCategories("Name");

        assertEquals(expected, actual);
    }

    @Test
    void categoryExist() {

        Boolean found = categoryStorage.categoryExist("Name1");
        assertTrue(found);

        found = categoryStorage.categoryExist("NameNotExist");
        assertFalse(found);
    }

    @Test
    void getCategoriesNoMatch() {
        ArrayList<Category> expectedCategories = new ArrayList<>();

        ArrayList<Category> categories = categoryStorage.getCategories("NameNotExist");
        assertEquals(expectedCategories, categories);
    }

    @Test
    void getCategoriesExactMatch() {
        ArrayList<Category> expectedCategories = new ArrayList<>();
        expectedCategories.add(new Category("Name1", 100, false));

        ArrayList<Category> categories = categoryStorage.getCategories("Name1");

        assertEquals(expectedCategories, categories);
    }

    @Test
    void getCategoriesMultipleMatch() {
        ArrayList<Category> expectedCategories = new ArrayList<>();
        expectedCategories.add(new Category("Name1", 100, false));
        expectedCategories.add(new Category("Name2", 200, true));
        expectedCategories.add(new Category("Name3", 300, false));

        ArrayList<Category> categories = categoryStorage.getCategories("Name");

        assertEquals(expectedCategories, categories);
    }

    @Test
    void updateCategoryAmount() {
        Category expected = new Category("Name2", 100, true);
        ArrayList<Category> expectedResults = new ArrayList<>();
        expectedResults.add(expected);

        categoryStorage.updateAmount("Name2", 100);

        ArrayList<Category> actualResults = categoryStorage.getCategories("Name2");

        assertEquals(expectedResults, actualResults);
    }

    @Test
    void clearCategoryAmount() {
        Category expected = new Category("Name2", Float.NaN, true);
        ArrayList<Category> expectedResults = new ArrayList<>();
        expectedResults.add(expected);

        categoryStorage.updateAmount("Name2", Float.NaN);

        ArrayList<Category> actualResults = categoryStorage.getCategories("Name2");

        assertEquals(expectedResults, actualResults);
    }

    @Test
    void toggleExclusion() {
        Category expected = new Category("Name2", 200, false);
        ArrayList<Category> expectedResults = new ArrayList<>();
        expectedResults.add(expected);

        categoryStorage.toggleExclusion("Name2");

        ArrayList<Category> actualResults = categoryStorage.getCategories("Name2");

        assertEquals(expectedResults, actualResults);
    }

    @Test
    void renameCategory() {
        Category expected = new Category("Name5", 200, true);
        ArrayList<Category> expectedResults = new ArrayList<>();
        expectedResults.add(expected);

        categoryStorage.renameCategory("Name2", "Name5");

        ArrayList<Category> actualResults = categoryStorage.getCategories("Name5");

        assertEquals(expectedResults, actualResults);
    }

    @AfterEach
    void dropTable() {
        connection.executeUpdate("DROP TABLE " + testTable);
        connection.close();
    }
}