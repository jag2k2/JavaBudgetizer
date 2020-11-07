package flb.application.category.listeners;

import static org.junit.jupiter.api.Assertions.*;

import flb.components.editors.tables.listeners.UserAddsCategoryListener;
import flb.datastores.CategoryStore;
import flb.components.editors.CategoryEditorImpl;
import flb.components.editors.CategoryAdder;
import flb.tuples.*;
import flb.datastores.*;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.*;

class UserAddsCategoryListenerTest {
    private JTextField nameFilter;
    private JButton testButton;
    private ArrayList<Category> expected;
    private TestDatabase database;
    private CategoryStore categoryStore;

    @BeforeEach
    void setUp() {
        this.database = new TestDatabase();
        database.connect();
        this.categoryStore = new CategoryStoreImpl(database);
        CategoryAdder categoryAdder = new CategoryEditorImpl(categoryStore);

        this.nameFilter = new JTextField();
        this.testButton = new JButton();

        this.expected = new ArrayList<>();
        expected.add(new Category("Name1", 100, false));
        expected.add(new Category("Name2", 200, true));
        expected.add(new Category("Name3", 300, false));
        expected.add(new Category("Test1::sub1", Float.NaN, false));
        expected.add(new Category("Test1::sub2", 500, true));

        testButton.addActionListener(new UserAddsCategoryListener(categoryAdder, nameFilter));
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void categoryAdded() {
        expected.add(new Category("Test2", Float.NaN, false));
        nameFilter.setText("Test2");

        testButton.doClick();

        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals("", nameFilter.getText());
    }

    @Test
    void emptyNameNotAdded() {
        nameFilter.setText("");

        testButton.doClick();

        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals("", nameFilter.getText());
    }

    @Test
    void duplicateNameNotAdded() {
        nameFilter.setText("Name1");

        testButton.doClick();

        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals("Name1", nameFilter.getText());
    }
}