import java.util.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class CategoryStorageTest {

    private final static String testTable = "categories";
    private final static String databaseName = "test";
    private CategoryStorage categoryStorage;


    @BeforeAll
    static void initTable() {
        DatabaseConnection connection = new DatabaseConnection(databaseName);
        String update = "CREATE TABLE test." + testTable +
                " ( `id` INT NOT NULL AUTO_INCREMENT, " +
                "`name` VARCHAR(255) NOT NULL , " +
                "`default_goal_amt` FLOAT(9.2) NULL DEFAULT NULL , " +
                "`exclude` BOOLEAN NOT NULL , " +
                "PRIMARY KEY (`id`)) ENGINE = InnoDB";
        connection.executeUpdate(update);

        update = "INSERT INTO " + testTable + " (name, default_goal_amt, exclude) " +
                "VALUES ('Name1', '100', '0'), ('Name2', '200', 1), ('Name3', '300', '0')";
        connection.executeUpdate(update);
        connection.close();
    }

    @BeforeEach
    void initClass(){
        categoryStorage = new CategoryStorage(databaseName);
    }

    @Test
    void categoryExist() {

        Boolean found = categoryStorage.categoryExist("Name1");
        assertTrue(found);

        found = categoryStorage.categoryExist("TestFail");
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

    @AfterAll
    static void dropTable() {
        DatabaseConnection connection = new DatabaseConnection(databaseName);
        connection.executeUpdate("DROP TABLE " + testTable);
        connection.close();
    }
}