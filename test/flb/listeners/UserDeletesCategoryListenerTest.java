package flb.listeners;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import flb.components.editor.category.CategoryEditorNoDialog;
import flb.databases.*;
import flb.components.editor.category.CategoryEditorImpl;
import flb.tuples.*;
import flb.datastores.*;
import javax.swing.*;
import java.util.*;

class UserDeletesCategoryListenerTest {
    private JButton testButton;
    private ArrayList<Category> expected;
    private TestDatabase database;
    private CategoryStore categoryStore;
    private CategoryEditorImpl categoryEditor;

    @BeforeEach
    void setUp() {
        database = new TestDatabase();
        database.connect();
        categoryStore = new DataStoreImpl(database);

        testButton = new JButton();

        expected = CategoryListFactory.makeDefaultCategories();
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void userConfirmsDeleteAndCategoryUnused() {
        categoryEditor = new CategoryEditorNoDialog(categoryStore, true);
        categoryEditor.update();

        testButton.addActionListener(new UserDeletesCategoryListener(categoryEditor, new JFrame()));

        String nameFilterText = "Test1";
        categoryEditor.setNameFilter(nameFilterText);
        testButton.setActionCommand("0");
        testButton.doClick();

        expected.remove(3);
        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals(nameFilterText, categoryEditor.getNameFilter());
    }

    @Test
    void userConfirmsDeleteButCategoryUsed() {
        categoryEditor = new CategoryEditorNoDialog(categoryStore, true);
        categoryEditor.update();

        testButton.addActionListener(new UserDeletesCategoryListener(categoryEditor, new JFrame()));

        String nameFilterText = "Name";
        categoryEditor.setNameFilter(nameFilterText);
        testButton.setActionCommand("1");
        testButton.doClick();

        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals(nameFilterText, categoryEditor.getNameFilter());
    }

    @Test
    void userRefusesDelete() {
        categoryEditor = new CategoryEditorNoDialog(categoryStore, false);

        testButton.addActionListener(new UserDeletesCategoryListener(categoryEditor, new JFrame()));

        String nameFilterText = "Name";
        categoryEditor.setNameFilter(nameFilterText);
        testButton.setActionCommand("1");
        testButton.doClick();

        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals(nameFilterText, categoryEditor.getNameFilter());
    }

    @Test
    void userDeletesWithNoSelected() {
        categoryEditor = new CategoryEditorImpl(categoryStore);

        testButton.addActionListener(new UserDeletesCategoryListener(categoryEditor, new JFrame()));

        String nameFilterText = "Name";
        categoryEditor.setNameFilter(nameFilterText);
        testButton.setActionCommand("-1");
        testButton.doClick();

        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals(nameFilterText, categoryEditor.getNameFilter());
    }
}