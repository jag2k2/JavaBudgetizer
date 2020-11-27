package flb.listeners;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import flb.databases.*;
import flb.datastores.*;
import flb.components.editor.category.CategoryEditorImpl;
import flb.tuples.*;
import javax.swing.*;
import java.util.*;

class UserClearsDefaultGoalListenerTest {
    private JButton testButton;
    private ArrayList<Category> expected;
    private CategoryEditorImpl categoryEditor;
    private TestDatabase database;
    private CategoryStore categoryStore;

    @BeforeEach
    void setUp() {
        database = new TestDatabase();
        database.connect();
        categoryStore = new CategoryStoreImpl(database);
        categoryEditor = new CategoryEditorImpl(categoryStore);
        categoryEditor.update();

        testButton = new JButton();
        testButton.addActionListener(new UserClearsDefaultGoalListener(categoryEditor));

        expected = CategoryListFactory.makeDefaultCategories();
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void clearSelected() {
        String nameFilterText = "Name";
        categoryEditor.setNameFilter(nameFilterText);

        testButton.setActionCommand("0");
        testButton.doClick();

        expected.set(1, new Category("Name2", true));
        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals(nameFilterText, categoryEditor.getNameFilter());
    }

    @Test
    void nothingSelected() {
        String nameFilterText = "Name";
        categoryEditor.setNameFilter(nameFilterText);
        testButton.setActionCommand("-1");
        testButton.doClick();

        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals(nameFilterText, categoryEditor.getNameFilter());
    }
}