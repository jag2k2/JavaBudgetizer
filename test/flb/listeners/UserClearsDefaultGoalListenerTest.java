package flb.listeners;

import static org.junit.jupiter.api.Assertions.*;

import flb.components.editors.CategoryEditorTester;
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
    private JButton testButton;
    private ArrayList<Category> expected;
    private CategoryTableTester tableAutomator;
    private CategoryEditorTester editorTester;
    private TestDatabase database;
    private CategoryStore categoryStore;

    @BeforeEach
    void setUp() {
        database = new TestDatabase();
        database.connect();
        categoryStore = new CategoryStoreImpl(database);
        CategoryEditorImpl categoryEditor = new CategoryEditorImpl(categoryStore);
        categoryEditor.update();
        editorTester = categoryEditor;
        tableAutomator = categoryEditor.getTableTester();

        testButton = new JButton();
        testButton.addActionListener(new UserClearsDefaultGoalListener(categoryEditor));

        expected = TestDatabase.getTestCategories();
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void clearSelected() {
        String nameFilterText = "Name";
        editorTester.setNameFilter(nameFilterText);

        testButton.setActionCommand("0");
        testButton.doClick();

        expected.set(1, new Category("Name2", true));
        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals(nameFilterText, editorTester.getNameFilter());
    }

    @Test
    void nothingSelected() {
        String nameFilterText = "Name";
        editorTester.setNameFilter(nameFilterText);
        testButton.setActionCommand("-1");
        testButton.doClick();

        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals(nameFilterText, editorTester.getNameFilter());
    }
}