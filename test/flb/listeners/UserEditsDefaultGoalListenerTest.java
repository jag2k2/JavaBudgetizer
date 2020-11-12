package flb.listeners;

import static org.junit.jupiter.api.Assertions.assertEquals;
import flb.components.monthselector.MonthSelectorImpl;
import flb.datastores.CategoryStore;
import flb.components.editors.CategoryEditorImpl;
import flb.components.editors.tables.CategoryTableTester;
import flb.tuples.*;
import flb.datastores.*;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.*;

class UserEditsDefaultGoalListenerTest {
    private JTextField nameFilter;
    private ArrayList<Category> expected;
    private CategoryTableTester tableAutomator;
    private TestDatabase database;
    private CategoryStore categoryStore;

    @BeforeEach
    void setUp() {
        this.database = new TestDatabase();
        database.connect();
        this.categoryStore = new CategoryStoreImpl(database);
        CategoryEditorImpl categoryEditor = new CategoryEditorImpl(categoryStore);
        this.tableAutomator = categoryEditor.getTableTester();
        this.nameFilter = new JTextField();

        this.expected = TestDatabase.getTestCategories();
        categoryEditor.refreshAndKeepSelection("");

        categoryEditor.addCategoryEditingListeners(nameFilter, new JFrame(), new MonthSelectorImpl());
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void editCategoryGoal() {
        int activeRow = 0;
        float newDefaultGoal = 200;

        nameFilter.setText("Name");
        tableAutomator.setSelectedRow(activeRow);

        tableAutomator.editCellAt(activeRow,1);
        tableAutomator.setEditorGoal(newDefaultGoal);

        Category category = TestDatabase.getTestCategories().get(activeRow);
        category.setDefaultGoal(newDefaultGoal);
        expected.set(0, category);
        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }
}