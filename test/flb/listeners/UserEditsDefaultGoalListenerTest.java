package flb.listeners;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.*;
import flb.databases.*;
import flb.components.editor.category.CategoryEditorImpl;
import flb.components.editor.category.CategoryTableTester;
import flb.tuples.*;
import flb.datastores.*;
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

        expected = CategoryListFactory.makeDefaultCategories();
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

        Category category = CategoryListFactory.makeDefaultCategories().get(1);
        category.setDefaultGoal(newDefaultGoal);
        expected.set(1, category);
        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals(nameFilterText, categoryEditor.getNameFilter());
    }
}