package flb.category.application.listeners;

import flb.category.*;
import flb.category.application.*;
import flb.database.TestDatabase;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class UserClearsGoalListenerTest {
    private UserClearsGoalListener clearListener;
    private TestDatabase database;
    private CategoryStorage categoryStorage;
    private JTextField nameFilter;
    private ArrayList<Category> expectedStored;
    private JTable table;
    private JButton testButton;

    @BeforeEach
    void setUp() {
        this.testButton = new JButton();
        this.nameFilter = new JTextField();
        this.database = new TestDatabase();
        database.connect();
        this.categoryStorage = new CategoryStorage(database);
        CategoryTableModel tableModel = new CategoryTableModel(categoryStorage.getCategories(""));
        this.table = new JTable(tableModel);
        CategoryTable categoryTable = new CategoryTable(table, tableModel);
        CategoryTableEditor tableEditor = new CategoryTableEditor(categoryStorage, categoryTable);
        this.clearListener = new UserClearsGoalListener(tableEditor, nameFilter);

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
    void clearSelected() {
        nameFilter.setText("Name");
        testButton.addActionListener(clearListener);
        table.getSelectionModel().setSelectionInterval(1,1);
        testButton.doClick();

        expectedStored.set(1, new Category("Name2", Float.NaN, true));
        assertEquals(expectedStored, categoryStorage.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }

    @Test
    void nothingSelectedNothingCleared() {
        nameFilter.setText("Name");
        testButton.addActionListener(clearListener);
        table.getSelectionModel().setSelectionInterval(-1,-1);
        testButton.doClick();

        assertEquals(expectedStored, categoryStorage.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }
}