package flb.category.application.listeners;

import flb.category.*;
import flb.category.application.*;
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
    private CategoryTableEditor tableEditor;

    @BeforeEach
    void setUp() {
        this.testButton = new JButton();
        this.nameFilter = new JTextField();
        this.database = new TestDatabase();
        database.connect();
        this.categoryStorage = new CategoryStorage(database);
        CategoryTableModel tableModel = new CategoryTableModel(categoryStorage.getCategories(""));
        this.table = new JTable(tableModel);
        categoryTable = new CategoryTable(table, tableModel);

        this.expectedStored = new ArrayList<>();
        expectedStored.add(new Category("Name1", 100, false));
        expectedStored.add(new Category("Name2", 200, true));
        expectedStored.add(new Category("Name3", 300, false));
        expectedStored.add(new Category("Test1", Float.NaN, false));
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void userConfirmsDelete() {
        tableEditor = new TableEditorNoDialog(categoryStorage, categoryTable, true);
        testButton.addActionListener(new UserDeletesCategoryListener(tableEditor, nameFilter, new JFrame()));

        nameFilter.setText("Name");
        table.getSelectionModel().setSelectionInterval(1,1);
        testButton.doClick();

        expectedStored.remove(1);
        assertEquals(expectedStored, categoryStorage.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }

    @Test
    void userRefusesDelete() {
        tableEditor = new TableEditorNoDialog(categoryStorage, categoryTable, false);
        testButton.addActionListener(new UserDeletesCategoryListener(tableEditor, nameFilter, new JFrame()));

        nameFilter.setText("Name");
        table.getSelectionModel().setSelectionInterval(1,1);
        testButton.doClick();

        assertEquals(expectedStored, categoryStorage.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }

    @Test
    void userDeletesWithNoSelected() {
        tableEditor = new CategoryTableEditor(categoryStorage, categoryTable);
        testButton.addActionListener(new UserDeletesCategoryListener(tableEditor, nameFilter, new JFrame()));

        nameFilter.setText("Name");
        table.getSelectionModel().setSelectionInterval(-1,-1);
        testButton.doClick();

        assertEquals(expectedStored, categoryStorage.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }
}