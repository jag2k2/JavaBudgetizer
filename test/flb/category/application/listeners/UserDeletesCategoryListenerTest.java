package flb.category.application.listeners;

import flb.category.*;
import flb.category.application.*;
import flb.category.application.CategoryTableEditor;
import flb.database.*;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class UserDeletesCategoryListenerTest {
    private TestDatabase database;
    private CategoryStorage categoryStorage;
    private JTextField nameFilter;
    private ArrayList<Category> expectedStored;
    private JTable table;
    private JButton testButton;
    private CategoryTable categoryTable;
    private CategoryDeleter categoryDeleter;

    @BeforeEach
    void setUp() {
        this.expectedStored = new ArrayList<>();
        expectedStored.add(new Category("Name1", 100, false));
        expectedStored.add(new Category("Name2", 200, true));
        expectedStored.add(new Category("Name3", 300, false));
        expectedStored.add(new Category("Test1", Float.NaN, false));

        this.nameFilter = new JTextField();
        this.database = new TestDatabase();
        database.connect();
        this.categoryStorage = new CategoryStorageImp(database);
        CategoryTableModel tableModel = new CategoryTableModel();
        tableModel.setContents(expectedStored);
        this.table = new JTable(tableModel);
        categoryTable = new CategoryTable(table, tableModel);

        this.testButton = new JButton();
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void userConfirmsDelete() {
        categoryDeleter = new TableEditorNoDialog(categoryStorage, categoryTable, true);
        testButton.addActionListener(new UserDeletesCategoryListener(categoryDeleter, nameFilter, new JFrame()));

        nameFilter.setText("Name");
        table.getSelectionModel().setSelectionInterval(1,1);
        testButton.doClick();

        expectedStored.remove(1);
        assertEquals(expectedStored, categoryStorage.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }

    @Test
    void userRefusesDelete() {
        categoryDeleter = new TableEditorNoDialog(categoryStorage, categoryTable, false);
        testButton.addActionListener(new UserDeletesCategoryListener(categoryDeleter, nameFilter, new JFrame()));

        nameFilter.setText("Name");
        table.getSelectionModel().setSelectionInterval(1,1);
        testButton.doClick();

        assertEquals(expectedStored, categoryStorage.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }

    @Test
    void userDeletesWithNoSelected() {
        categoryDeleter = new CategoryTableEditor(categoryStorage, categoryTable);
        testButton.addActionListener(new UserDeletesCategoryListener(categoryDeleter, nameFilter, new JFrame()));

        nameFilter.setText("Name");
        table.getSelectionModel().setSelectionInterval(-1,-1);
        testButton.doClick();

        assertEquals(expectedStored, categoryStorage.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }
}