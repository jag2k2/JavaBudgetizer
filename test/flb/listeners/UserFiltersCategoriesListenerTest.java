package flb.listeners;

import static org.junit.jupiter.api.Assertions.*;

import flb.datastores.CategoryStore;
import flb.components.editors.CategoryEditorImpl;
import flb.components.editors.tables.CategoryTableTester;
import flb.tuples.*;
import flb.datastores.*;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.*;

class UserFiltersCategoriesListenerTest {
    private JTextField nameFilter;
    private ArrayList<Category> expected;
    private CategoryTableTester tableAutomator;
    private TestDatabase database;

    @BeforeEach
    void setUp() {
        this.database = new TestDatabase();
        database.connect();
        CategoryStore categoryStore = new CategoryStoreImpl(database);
        CategoryEditorImpl categoryEditor = new CategoryEditorImpl(categoryStore);
        this.tableAutomator = categoryEditor.getTableTester();
        this.nameFilter = new JTextField();

        this.expected = TestDatabase.getTestCategories();

        nameFilter.getDocument().addDocumentListener(new UserFiltersCategoriesListener(categoryEditor, nameFilter));
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void insertUpdate() {
        nameFilter.setText("N");
        expected.remove(3);
        expected.remove(3);
        assertEquals(expected, tableAutomator.getContents());
    }

    @Test
    void removeUpdate() {
        nameFilter.setText("Name1");
        nameFilter.setText("Name");
        expected.remove(3);
        expected.remove(3);
        assertEquals(expected, tableAutomator.getContents());
    }
}