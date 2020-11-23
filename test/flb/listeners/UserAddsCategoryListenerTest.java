package flb.listeners;

import static org.junit.jupiter.api.Assertions.*;

import flb.databases.TestDatabase;
import flb.datastores.CategoryStore;
import flb.components.editors.CategoryEditorImpl;
import flb.components.editors.CategoryAdder;
import flb.tuples.*;
import flb.datastores.*;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.*;

class UserAddsCategoryListenerTest {
    private JButton testButton;
    private ArrayList<Category> expected;
    private TestDatabase database;
    private CategoryStore categoryStore;
    private CategoryEditorImpl categoryEditor;

    @BeforeEach
    void setUp() {
        database = new TestDatabase();
        database.connect();
        categoryStore = new CategoryStoreImpl(database);
        categoryEditor = new CategoryEditorImpl(categoryStore);
        testButton = new JButton();

        expected = TestDatabase.getTestCategories();

        testButton.addActionListener(new UserAddsCategoryListener(categoryEditor));
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void categoryAdded() {
        String nameFilterText = "Test2";
        expected.add(new Category(nameFilterText, false));
        categoryEditor.setNameFilter(nameFilterText);

        testButton.doClick();

        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals("", categoryEditor.getNameFilter());
    }

    @Test
    void emptyNameNotAdded() {
        String nameFilterText = "";
        categoryEditor.setNameFilter(nameFilterText);

        testButton.doClick();

        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals(nameFilterText, categoryEditor.getNameFilter());
    }

    @Test
    void duplicateNameNotAdded() {
        String nameFilterText = "Name2";
        categoryEditor.setNameFilter(nameFilterText);

        testButton.doClick();

        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals(nameFilterText, categoryEditor.getNameFilter());
    }
}