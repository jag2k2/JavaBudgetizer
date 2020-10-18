package flb.category.application.listeners;

import flb.category.application.*;
import flb.category.*;
import flb.database.*;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.*;
import javax.swing.event.ChangeEvent;
import static org.junit.jupiter.api.Assertions.*;

class UserRenamesSelectionListenerTest {
    private TestDatabase database;
    private CategoryStorage categoryStorage;
    private JTable table;
    private ArrayList<Category> expectedStored;
    private JTextField nameFilter;

    @BeforeEach
    void setUp() {
        this.nameFilter = new JTextField();
        this.database = new TestDatabase();
        database.connect();
        this.categoryStorage = new CategoryStorage(database);
        CategoryTableModel tableModel = new CategoryTableModel(categoryStorage.getCategories(""));
        this.table = new JTable(tableModel);
        CategoryTable categoryTable = new CategoryTable(table, tableModel);
        CategoryTableEditor tableEditor = new CategoryTableEditor(categoryStorage, categoryTable);
        table.addPropertyChangeListener(new UserRenamesSelectionListener(tableEditor, nameFilter));

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
    void renameFirstCategory() {
        nameFilter.setText("Name");
        table.getSelectionModel().setSelectionInterval(0,0);

        table.editCellAt(0,0);
        table.setCellEditor(new DefaultCellEditor(new JTextField("Name10")));
        table.editingStopped(new ChangeEvent(table));

        expectedStored.set(0, new Category("Name10", 100, false));
        assertEquals(expectedStored, categoryStorage.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }

    @Test
    void renameLastCategory() {
        nameFilter.setText("Name");
        table.getSelectionModel().setSelectionInterval(3,3);

        table.editCellAt(3,0);
        table.setCellEditor(new DefaultCellEditor(new JTextField("Test10")));
        table.editingStopped(new ChangeEvent(table));

        expectedStored.set(3, new Category("Test10", Float.NaN, false));
        assertEquals(expectedStored, categoryStorage.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }
}