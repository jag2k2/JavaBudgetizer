package flb.application.category.listeners;

import static org.junit.jupiter.api.Assertions.*;

import flb.components.editors.CategoryEditorNoDialog;
import flb.datastores.CategoryStore;
import flb.components.editors.CategoryEditorImpl;
import flb.components.editors.tables.CategoryTableTester;
import flb.tuples.*;
import flb.datastores.*;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.*;

class UserDeletesCategoryListenerTest {
    private JTextField nameFilter;
    private JButton testButton;
    private ArrayList<Category> expected;
    private TestDatabase database;
    private CategoryStore categoryStore;
    private CategoryTableTester tableAutomator;
    private CategoryEditorImpl categoryEditor;

    @BeforeEach
    void setUp() {
        this.database = new TestDatabase();
        database.connect();
        this.categoryStore = new CategoryStoreImpl(database);

        this.nameFilter = new JTextField();
        this.testButton = new JButton();

        this.expected = new ArrayList<>();
        expected.add(new Category("Name1", 100, false));
        expected.add(new Category("Name2", 200, true));
        expected.add(new Category("Name3", 300, false));
        expected.add(new Category("Test1::sub1", Float.NaN, false));
        expected.add(new Category("Test1::sub2", 500, true));
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void userConfirmsDelete() {
        categoryEditor = new CategoryEditorNoDialog(categoryStore, true);
        categoryEditor.refreshAndClearSelection("");
        this.tableAutomator = categoryEditor.getTableAutomator();

        testButton.addActionListener(new UserDeletesCategoryListener(categoryEditor, nameFilter, new JFrame()));

        nameFilter.setText("Name");
        tableAutomator.setSelectedRow(1);
        testButton.doClick();

        expected.remove(1);
        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }

    @Test
    void userRefusesDelete() {
        categoryEditor = new CategoryEditorNoDialog(categoryStore, false);
        categoryEditor.refreshAndClearSelection("");
        this.tableAutomator = categoryEditor.getTableAutomator();

        testButton.addActionListener(new UserDeletesCategoryListener(categoryEditor, nameFilter, new JFrame()));

        nameFilter.setText("Name");
        tableAutomator.setSelectedRow(1);
        testButton.doClick();

        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }

    @Test
    void userDeletesWithNoSelected() {
        categoryEditor = new CategoryEditorImpl(categoryStore);
        this.tableAutomator = categoryEditor.getTableAutomator();

        testButton.addActionListener(new UserDeletesCategoryListener(categoryEditor, nameFilter, new JFrame()));

        nameFilter.setText("Name");
        tableAutomator.setSelectedRow(-1);
        testButton.doClick();

        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }
}