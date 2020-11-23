package flb.listeners;

import static org.junit.jupiter.api.Assertions.*;

import flb.databases.TestDatabase;
import flb.datastores.CategoryStore;
import flb.components.editors.CategoryEditorImpl;
import flb.components.editors.tables.CategoryTableTester;
import flb.tuples.*;
import flb.datastores.*;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.*;

class UserFiltersCategoriesListenerTest {
    private ArrayList<Category> expected;
    private CategoryTableTester tableAutomator;
    private CategoryEditorImpl categoryEditor;
    private TestDatabase database;

    @BeforeEach
    void setUp() {
        database = new TestDatabase();
        database.connect();
        CategoryStore categoryStore = new CategoryStoreImpl(database);
        categoryEditor = new CategoryEditorImpl(categoryStore);
        tableAutomator = categoryEditor.getTableTester();

        expected = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void removeUpdate() {
        String filterString = "Name";
        categoryEditor.setNameFilter(filterString);
        for (Category category : TestDatabase.getTestCategories()){
            if(category.getName().contains(filterString)){
                expected.add(category);
            }
        }

        assertEquals(expected, tableAutomator.getContents());
    }
}