package flb.application.category.listeners;

import static org.junit.jupiter.api.Assertions.*;
import flb.tables.category.*;
import flb.tuples.*;
import flb.database.*;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.*;

class UserDeletesCategoryListenerTest {
    private JTextField nameFilter;
    private JButton testButton;
    private ArrayList<Category> expected;
    private TestDatabase database;
    private CategoryStore categoryStore;
    private CategoryTable categoryTable;
    private CategoryTableAutomator tableAutomator;
    private CategoryDeleter categoryDeleter;

    @BeforeEach
    void setUp() {
        this.nameFilter = new JTextField();
        this.testButton = new JButton();
        CategoryTableImp categoryTableImp = new CategoryTableImp();
        this.categoryTable = categoryTableImp;
        this.tableAutomator = categoryTableImp;

        this.expected = new ArrayList<>();
        expected.add(new Category("Name1", 100, false));
        expected.add(new Category("Name2", 200, true));
        expected.add(new Category("Name3", 300, false));
        expected.add(new Category("Test1::sub1", Float.NaN, false));
        expected.add(new Category("Test1::sub2", 500, true));

        this.database = new TestDatabase();
        database.connect();
        this.categoryStore = new CategoryStoreImpl(database);
        categoryTable.displayAndClearSelection(expected);
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void userConfirmsDelete() {
        categoryDeleter = new CategoryEditorNoDialog(categoryStore, categoryTable, true);
        testButton.addActionListener(new UserDeletesCategoryListener(categoryDeleter, nameFilter, new JFrame()));

        nameFilter.setText("Name");
        tableAutomator.setSelectedRow(1);
        testButton.doClick();

        expected.remove(1);
        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }

    @Test
    void userRefusesDelete() {
        categoryDeleter = new CategoryEditorNoDialog(categoryStore, categoryTable, false);
        testButton.addActionListener(new UserDeletesCategoryListener(categoryDeleter, nameFilter, new JFrame()));

        nameFilter.setText("Name");
        tableAutomator.setSelectedRow(1);
        testButton.doClick();

        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }

    @Test
    void userDeletesWithNoSelected() {
        categoryDeleter = new CategoryEditorImp(categoryStore, categoryTable);
        testButton.addActionListener(new UserDeletesCategoryListener(categoryDeleter, nameFilter, new JFrame()));

        nameFilter.setText("Name");
        tableAutomator.setSelectedRow(-1);
        testButton.doClick();

        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }
}