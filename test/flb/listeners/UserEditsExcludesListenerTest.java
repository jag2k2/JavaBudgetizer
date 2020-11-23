package flb.listeners;

import static org.junit.jupiter.api.Assertions.*;

import flb.databases.TestDatabase;
import flb.components.editor.category.CategoryEditorImpl;
import flb.components.editor.category.CategoryTableTester;
import flb.tuples.*;
import flb.datastores.*;
import org.junit.jupiter.api.*;

import java.util.*;

class UserEditsExcludesListenerTest {
    private ArrayList<Category> expected;
    private CategoryTableTester tableAutomator;
    private CategoryEditorImpl categoryEditor;
    private TestDatabase database;
    private CategoryStore categoryStore;

    @BeforeEach
    void setUp() {
        database = new TestDatabase();
        database.connect();
        categoryStore = new CategoryStoreImpl(database);
        categoryEditor = new CategoryEditorImpl(categoryStore);
        categoryEditor.update();
        tableAutomator = categoryEditor.getTableTester();

        expected = TestDatabase.getTestCategories();
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void enableDisabledCategory() {
        int activeRow = 0;

        String nameFilterText = "Name";
        categoryEditor.setNameFilter(nameFilterText);
        tableAutomator.setSelectedRow(activeRow);

        tableAutomator.toggleSelectedExcludes();

        Category category = TestDatabase.getTestCategories().get(1);
        category.toggleExcludes();
        expected.set(1, category);
        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals(nameFilterText, categoryEditor.getNameFilter());
    }

    @Test
    void disableEnabledCategory() {
        String nameFilterText = "Name";
        categoryEditor.setNameFilter(nameFilterText);
        tableAutomator.setSelectedRow(0);

        tableAutomator.toggleSelectedExcludes();

        expected.set(1, new Category("Name2", 200, false));
        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals(nameFilterText, categoryEditor.getNameFilter());
    }
}