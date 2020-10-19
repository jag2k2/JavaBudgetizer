package flb.category.application.listeners;

import flb.category.application.*;
import flb.category.*;
import flb.database.*;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class UserAddsCategoryListenerTest {
    private TestDatabase database;
    private CategoryStorage categoryStorage;
    private JTextField nameFilter;
    private ArrayList<Category> expectedStored;
    private JButton testButton;

    @BeforeEach
    void setUp() {
        this.nameFilter = new JTextField();
        this.database = new TestDatabase();
        database.connect();
        this.categoryStorage = new CategoryStorage(database);
        CategoryTableModel tableModel = new CategoryTableModel(categoryStorage.getCategories(""));
        JTable table = new JTable(tableModel);
        CategoryTable categoryTable = new CategoryTable(table, tableModel);
        CategoryTableEditor tableEditor = new CategoryTableEditor(categoryStorage, categoryTable);

        this.testButton = new JButton();
        testButton.addActionListener(new UserAddsCategoryListener(tableEditor, nameFilter));

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
    void categoryAdded() {
        nameFilter.setText("Test2");
        testButton.doClick();

        expectedStored.add(new Category("Test2", Float.NaN, false));
        assertEquals(expectedStored, categoryStorage.getCategories(""));
        assertEquals("", nameFilter.getText());
    }

    @Test
    void emptyNameNotAdded() {
        nameFilter.setText("");
        testButton.doClick();

        assertEquals(expectedStored, categoryStorage.getCategories(""));
        assertEquals("", nameFilter.getText());
    }

    @Test
    void duplicateNameNotAdded() {
        nameFilter.setText("Name1");
        testButton.doClick();

        assertEquals("Name1", nameFilter.getText());
    }
}