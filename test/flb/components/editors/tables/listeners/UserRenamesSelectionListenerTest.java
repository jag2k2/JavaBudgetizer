package flb.components.editors.tables.listeners;

import static org.junit.jupiter.api.Assertions.*;

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
        this.tableAutomator = categoryEditor.getTableAutomator();
        this.nameFilter = new JTextField();
        categoryEditor.refreshAndClearSelection("");

        this.expected = new ArrayList<>();
        expected.add(new Category("Name1", 100, false));
        expected.add(new Category("Name2", 200, true));
        expected.add(new Category("Name3", 300, false));
        expected.add(new Category("Test1::sub1", Float.NaN, false));
        expected.add(new Category("Test1::sub2", 500, true));

        categoryEditor.addCategoryEditingListeners(nameFilter);
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void renameFirstCategory() {
        nameFilter.setText("Name");
        tableAutomator.setSelectedRow(0);

        tableAutomator.editCellAt(0,0);
        tableAutomator.setEditorName("Name10");

        expected.set(0, new Category("Name10", 100, false));
        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals("Name", nameFilter.getText());
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