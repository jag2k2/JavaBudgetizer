package flb.category.application.listeners;

import flb.category.application.*;
import flb.category.*;
import flb.database.*;
import org.junit.jupiter.api.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

import static org.junit.jupiter.api.Assertions.*;

class UserClearsGoalListenerTest {
    private CategoryStorage categoryStorage;
    private TestDatabase database;
    private CategoryTableModel tableModel;
    private JTable table;
    private UserClearsGoalListener addsListener;
    private JTextField nameFilter;
    private final ActionEvent actionEvent = new ActionEvent(this, 0, "test");
    private ArrayList<Category> actualStored;
    private ArrayList<Category> actualDisplayed;
    private ArrayList<Category> expectedStored;
    private ArrayList<Category> expectedDisplayed;

    @BeforeEach
    void setUp() {
        expectedStored = new ArrayList<>();
        actualStored = new ArrayList<>();
        expectedDisplayed = new ArrayList<>();
        actualDisplayed = new ArrayList<>();

        database = new TestDatabase();
        database.connect();
        this.categoryStorage = new CategoryStorage(database);
        this.tableModel = new CategoryTableModel(categoryStorage.getCategories(""));
        this.nameFilter = new JTextField();
        this.table = new JTable(tableModel);
        CategoryTable categoryTable = new CategoryTable(table, tableModel);
        this.addsListener = new UserClearsGoalListener(categoryStorage, categoryTable, nameFilter);
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void clearSelectedGoal() {
        nameFilter.setText("Name");
        table.getSelectionModel().setSelectionInterval(1,1);
        expectedStored.add(new Category("Name1", 100, false));
        expectedStored.add(new Category("Name2", Float.NaN, true));
        expectedStored.add(new Category("Name3", 300, false));
        expectedStored.add(new Category("Test1", Float.NaN, false));

        expectedDisplayed.add(new Category("Name1", 100, false));
        expectedDisplayed.add(new Category("Name2", Float.NaN, true));
        expectedDisplayed.add(new Category("Name3", 300, false));

        addsListener.actionPerformed(actionEvent);

        actualStored = categoryStorage.getCategories("");
        actualDisplayed = tableModel.getContents();
        assertEquals(expectedStored, actualStored);
        assertEquals(expectedDisplayed, actualDisplayed);
        assertEquals("Name", nameFilter.getText());
    }

    @Test
    void clearNoGoalSelected() {
        nameFilter.setText("");
        table.getSelectionModel().setSelectionInterval(-1,-1);
        expectedStored.add(new Category("Name1", 100, false));
        expectedStored.add(new Category("Name2", 200, true));
        expectedStored.add(new Category("Name3", 300, false));
        expectedStored.add(new Category("Test1", Float.NaN, false));
        expectedDisplayed = expectedStored;

        addsListener.actionPerformed(actionEvent);

        actualStored = categoryStorage.getCategories("");
        actualDisplayed = tableModel.getContents();
        assertEquals(expectedStored, actualStored);
        assertEquals(expectedDisplayed, actualDisplayed);
        assertEquals("", nameFilter.getText());
    }
}