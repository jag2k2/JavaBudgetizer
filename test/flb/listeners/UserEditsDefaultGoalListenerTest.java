package flb.listeners;

import static org.junit.jupiter.api.Assertions.assertEquals;

import flb.databases.TestDatabase;
import flb.components.editor.category.CategoryEditorImpl;
import flb.components.editor.category.CategoryTableTester;
import flb.tuples.*;
import flb.datastores.*;
import org.junit.jupiter.api.*;

import java.util.*;

class UserEditsDefaultGoalListenerTest {
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
        tableAutomator = categoryEditor.getTableTester();

        expected = TestDatabase.getTestCategories();
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void editCategoryGoal() {
        int activeRow = 0;
        float newDefaultGoal = 543;

        String nameFilterText = "Name";
        categoryEditor.setNameFilter(nameFilterText);
        tableAutomator.setSelectedRow(activeRow);

        tableAutomator.editCellAt(activeRow,1);
        tableAutomator.setEditorGoal(newDefaultGoal);

        Category category = TestDatabase.getTestCategories().get(1);
        category.setDefaultGoal(newDefaultGoal);
        expected.set(1, category);
        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals(nameFilterText, categoryEditor.getNameFilter());
    }
}