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

class UserEditsExcludesListenerTest {
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
        CategoryEditorImpl categoryEditorImpl = new CategoryEditorImpl(categoryStore);
        categoryEditorImpl.refreshAndKeepSelection("");
        this.tableAutomator = categoryEditorImpl.getTableTester();

        this.nameFilter = new JTextField();

        this.expected = TestDatabase.getTestCategories();

        categoryEditorImpl.addCategoryEditingListeners(nameFilter, new JFrame(), new MonthSelectorImpl());
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void enableDisabledCategory() {
        nameFilter.setText("Name");
        tableAutomator.setSelectedRow(0);

        tableAutomator.toggleSelectedExcludes();

        expected.set(0, new Category("Name1", 100, true));
        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }

    @Test
    void disableEnabledCategory() {
        nameFilter.setText("Name");
        tableAutomator.setSelectedRow(1);

        tableAutomator.toggleSelectedExcludes();

        expected.set(1, new Category("Name2", 200, false));
        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }
}