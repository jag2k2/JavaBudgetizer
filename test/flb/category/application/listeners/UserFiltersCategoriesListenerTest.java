package flb.category.application.listeners;

import flb.category.application.*;
import flb.category.*;
import flb.database.*;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class UserFiltersCategoriesListenerTest {
    private TestDatabase database;
    private ArrayList<Category> expectedStored;
    private JTextField nameFilter;
    private CategoryTableModel tableModel;

    @BeforeEach
    void setUp() {
        this.nameFilter = new JTextField();
        this.database = new TestDatabase();
        database.connect();
        CategoryStorage categoryStorage = new CategoryStorageImp(database);
        tableModel = new CategoryTableModel();
        JTable table = new JTable(tableModel);
        CategoryTable categoryTable = new CategoryTable(table, tableModel);
        CategoryTableEditor tableEditor = new CategoryTableEditor(categoryStorage, categoryTable);
        nameFilter.getDocument().addDocumentListener(new UserFiltersCategoriesListener(tableEditor, nameFilter));

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
    void insertUpdate() {
        nameFilter.setText("N");
        expectedStored.remove(3);
        assertEquals(expectedStored, tableModel.getContents());
    }

    @Test
    void removeUpdate() {
        nameFilter.setText("Name1");
        nameFilter.setText("Name");
        expectedStored.remove(3);
        assertEquals(expectedStored, tableModel.getContents());
    }
}