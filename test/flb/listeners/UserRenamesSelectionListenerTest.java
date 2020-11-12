package flb.listeners;

import static org.junit.jupiter.api.Assertions.*;

import flb.components.monthselector.MonthSelectorImpl;
import flb.datastores.CategoryStore;
import flb.components.editors.CategoryEditorImpl;
import flb.components.editors.tables.CategoryTableTester;
import flb.tuples.*;
import flb.datastores.*;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.*;

class UserRenamesSelectionListenerTest {
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
        categoryEditor.refreshAndClearSelection("");

        this.expected = TestDatabase.getTestCategories();

        categoryEditor.addCategoryEditingListeners(nameFilter, new JFrame(), new MonthSelectorImpl());
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void renameFirstCategory() {
        int activeRow = 0;
        String newName = "Name10";
        String filterString = "Name";

        nameFilter.setText(filterString);
        tableAutomator.setSelectedRow(activeRow);

        tableAutomator.editCellAt(activeRow,0);
        tableAutomator.setEditorName(newName);

        Category category = TestDatabase.getTestCategories().get(activeRow);
        category.rename(newName);
        expected.set(0, category);
        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals(filterString, nameFilter.getText());
    }

    @Test
    void renameLastCategory() {
        nameFilter.setText("Name");
        tableAutomator.setSelectedRow(4);

        tableAutomator.editCellAt(4,0);
        tableAutomator.setEditorName("Test20");

        expected.set(4, new Category("Test20", 500, true));
        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }
}