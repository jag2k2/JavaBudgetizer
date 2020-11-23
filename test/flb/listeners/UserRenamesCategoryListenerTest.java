package flb.listeners;

import static org.junit.jupiter.api.Assertions.*;
import flb.databases.TestDatabase;
import flb.components.editor.category.CategoryEditorImpl;
import flb.components.editor.category.CategoryTableTester;
import flb.tuples.*;
import flb.datastores.*;
import org.junit.jupiter.api.*;

import java.util.*;

class UserRenamesCategoryListenerTest {
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
    void renameFirstCategory() {
        int activeRow = 0;
        String filterString = "Name";
        String newName = "Name10";

        categoryEditor.setNameFilter(filterString);
        tableAutomator.setSelectedRow(activeRow);

        tableAutomator.editCellAt(activeRow,0);
        tableAutomator.setEditorName(newName);

        Category category = TestDatabase.getTestCategories().get(1);
        category.rename(newName);
        expected.set(1, category);
        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals(filterString, categoryEditor.getNameFilter());
    }

    @Test
    void renameLastCategory() {
        String filterString = "Test";
        String newName = "Test20";
        int activeRow = 1;

        categoryEditor.setNameFilter(filterString);
        tableAutomator.setSelectedRow(activeRow);

        tableAutomator.editCellAt(activeRow,0);
        tableAutomator.setEditorName(newName);

        expected.set(4, new Category(newName, 500, true));
        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals(filterString, categoryEditor.getNameFilter());
    }
}