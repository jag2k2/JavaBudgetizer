package flb.components;

import static org.junit.jupiter.api.Assertions.*;;
import flb.database.interfaces.*;
import flb.database.*;
import flb.tuples.*;
import org.junit.jupiter.api.*;
import java.util.*;

class CategoryMenuImplTest {
    private TestDatabase database;
    private CategoryMenuImpl categoryMenu;


    @BeforeEach
    void setUp() {
        this.database = new TestDatabase();
        database.connect();
        CategoryStore categoryStore = new CategoryStoreImpl(database);
        this.categoryMenu = new CategoryMenuImpl(categoryStore);
    }

    @AfterEach
    void tearDown() { this.database.close();}

    @Test
    void buildMenu() {
        ArrayList<String> expected = new ArrayList<>();
        for (Category category : TestDatabase.getTestCategories()) {
            expected.add(category.getName());
        }

        categoryMenu.buildMenu();

        assertEquals(expected, categoryMenu.toStringArray());
    }
}