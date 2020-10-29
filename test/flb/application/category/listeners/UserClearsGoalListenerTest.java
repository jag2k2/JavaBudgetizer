package flb.application.category.listeners;

import static org.junit.jupiter.api.Assertions.*;
import flb.database.*;
import flb.tables.category.*;
import flb.tuples.*;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.*;

class UserClearsGoalListenerTest {
    private JTextField nameFilter;
    private JButton testButton;
    private ArrayList<Category> expected;
    private CategoryTableAutomator tableAutomator;
    private TestDatabase database;
    private CategoryStore categoryStore;

    @BeforeEach
    void setUp() {
        this.nameFilter = new JTextField();
        this.testButton = new JButton();
        CategoryTableImp categoryTableImp = new CategoryTableImp();
        this.tableAutomator = categoryTableImp;

        this.expected = new ArrayList<>();
        expected.add(new Category("Name1", 100, false));
        expected.add(new Category("Name2", 200, true));
        expected.add(new Category("Name3", 300, false));
        expected.add(new Category("Test1::sub1", Float.NaN, false));
        expected.add(new Category("Test1::sub2", 500, true));
        ((CategoryTable) categoryTableImp).displayAndClearSelection(expected);

        this.database = new TestDatabase();
        database.connect();
        this.categoryStore = new CategoryStoreImpl(database);

        CategoryClearer categoryClearer = new CategoryEditorImp(categoryStore, categoryTableImp);
        testButton.addActionListener(new UserClearsGoalListener(categoryClearer, nameFilter));
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void clearSelected() {
        nameFilter.setText("Name");
        tableAutomator.setSelectedRow(1);
        testButton.doClick();

        expected.set(1, new Category("Name2", Float.NaN, true));
        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }

    @Test
    void nothingSelected() {
        nameFilter.setText("Name");
        tableAutomator.setSelectedRow(-1);
        testButton.doClick();

        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }
}