/*package flb.category.application;

import flb.category.Category;
import flb.category.CategoryStorage;
import org.junit.jupiter.api.*;
import flb.database.*;
import javax.swing.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MainGUITest {
    private TestDatabase database;
    private JTable table;
    private JTextField nameFilter;
    private MainGUI mainGui;
    private CategoryStorage categoryStorage;
    private CategoryTableModel tableModel;
    private ArrayList<Category> expectedStored;
    private ArrayList<Category> expectedDisplayed;

    @BeforeEach
    void setUp() {
        this.nameFilter = new JTextField();
        this.database = new TestDatabase();
        this.categoryStorage = new CategoryStorage(database);
        this.table = new JTable();
        database.connect();
        this.tableModel = new CategoryTableModel(categoryStorage.getCategories(""));
        this.mainGui = new MainGUI(database, table, tableModel, nameFilter);
        this.expectedStored = new ArrayList<>();
        this.expectedDisplayed = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void addingCategoryWithNoName() {
        nameFilter.setText("");
        expectedDisplayed = new ArrayList<>();
        expectedDisplayed.add(new Category("Name1", 100, false));
        expectedDisplayed.add(new Category("Name2", 200, true));
        expectedDisplayed.add(new Category("Name3", 300, false));
        expectedDisplayed.add(new Category("Test1", Float.NaN, false));
        expectedStored = expectedDisplayed;

        mainGui.userAddCategory();

        assertEquals(expectedStored, categoryStorage.getCategories(""));
        assertEquals(expectedDisplayed, tableModel.getContents());
        assertEquals("", nameFilter.getText());
    }

    @Test
    void addingNewCategory() {
        nameFilter.setText("Test2");
        expectedDisplayed = new ArrayList<>();
        expectedDisplayed.add(new Category("Name1", 100, false));
        expectedDisplayed.add(new Category("Name2", 200, true));
        expectedDisplayed.add(new Category("Name3", 300, false));
        expectedDisplayed.add(new Category("Test1", Float.NaN, false));
        expectedDisplayed.add(new Category("Test2", Float.NaN, false));
        expectedStored = expectedDisplayed;

        mainGui.userAddCategory();

        assertEquals(expectedStored, categoryStorage.getCategories(""));
        assertEquals(expectedDisplayed, tableModel.getContents());
        assertEquals("", nameFilter.getText());
    }

    @Test
    void addingDuplicateCategory() {
        nameFilter.setText("Name1");
        expectedDisplayed = new ArrayList<>();
        expectedDisplayed.add(new Category("Name1", 100, false));
        expectedDisplayed.add(new Category("Name2", 200, true));
        expectedDisplayed.add(new Category("Name3", 300, false));
        expectedDisplayed.add(new Category("Test1", Float.NaN, false));
        expectedStored = expectedDisplayed;

        mainGui.userAddCategory();

        assertEquals(expectedStored, categoryStorage.getCategories(""));
        assertEquals(expectedDisplayed, tableModel.getContents());
        assertEquals("Name1", nameFilter.getText());
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

        mainGui.userClearCategoryGoal();

        assertEquals(expectedStored, categoryStorage.getCategories(""));
        assertEquals(expectedDisplayed, tableModel.getContents());
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

        mainGui.userClearCategoryGoal();

        assertEquals(expectedStored, categoryStorage.getCategories(""));
        assertEquals(expectedDisplayed, tableModel.getContents());
        assertEquals("", nameFilter.getText());
    }
}*/