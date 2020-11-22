package flb.listeners;

import static org.junit.jupiter.api.Assertions.*;

import flb.databases.TestDatabase;
import flb.datastores.*;
import flb.datastores.CategoryStore;
import flb.components.editors.CategoryEditorImpl;
import flb.components.editors.tables.CategoryTableTester;
import flb.tuples.*;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.*;

class UserClearsDefaultGoalListenerTest {
    private JTextField nameFilter;
    private JButton testButton;
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
        categoryEditor.refreshAndKeepSelection("");
        this.tableAutomator = categoryEditor.getTableTester();

        this.nameFilter = new JTextField();
        this.testButton = new JButton();
        testButton.addActionListener(new UserClearsDefaultGoalListener(categoryEditor, nameFilter));

        this.expected = TestDatabase.getTestCategories();
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void clearSelected() {
        nameFilter.setText("Name");
        testButton.setActionCommand("1");
        testButton.doClick();

        expected.set(1, new Category("Name2", true));
        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }

    @Test
    void nothingSelected() {
        nameFilter.setText("Name");
        testButton.setActionCommand("-1");
        testButton.doClick();

        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }
}