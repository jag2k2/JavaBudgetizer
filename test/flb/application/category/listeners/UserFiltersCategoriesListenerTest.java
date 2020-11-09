package flb.application.category.listeners;

import static org.junit.jupiter.api.Assertions.*;

import flb.listeners.UserFiltersCategoriesListener;
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
        this.tableAutomator = categoryEditor.getTableAutomator();
        this.nameFilter = new JTextField();

        this.expected = new ArrayList<>();
        expected.add(new Category("Name1", 100, false));
        expected.add(new Category("Name2", 200, true));
        expected.add(new Category("Name3", 300, false));
        expected.add(new Category("Test1", Float.NaN, false));

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
        assertEquals(expected, tableAutomator.getContents());
    }

    @Test
    void removeUpdate() {
        nameFilter.setText("Name1");
        nameFilter.setText("Name");
        expected.remove(3);
        assertEquals(expected, tableAutomator.getContents());
    }
}