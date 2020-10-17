package flb.category.application.listeners;

import flb.category.application.*;
import flb.category.*;
import flb.database.*;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.*;
import javax.swing.event.ChangeEvent;
import java.beans.PropertyChangeEvent;
import static org.junit.jupiter.api.Assertions.*;

class UserRenamesSelectionListenerTest {
    private UserRenamesSelectionListener renamesListener;
    private TestDatabase database;
    private CategoryStorage categoryStorage;
    private JTable table;
    private ArrayList<Category> expectedStored;

    @BeforeEach
    void setUp() {
        JTextField nameFilter = new JTextField();
        this.database = new TestDatabase();
        database.connect();
        this.categoryStorage = new CategoryStorage(database);
        CategoryTableModel tableModel = new CategoryTableModel(categoryStorage.getCategories(""));
        this.table = new JTable(tableModel);
        CategoryTable categoryTable = new CategoryTable(table, tableModel);
        CategoryTableEditor tableEditor = new CategoryTableEditor(categoryStorage, categoryTable);
        this.renamesListener = new UserRenamesSelectionListener(tableEditor, nameFilter);

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
    void renameCanceled() {
        table.getSelectionModel().setSelectionInterval(0,0);
        table.editCellAt(0,0);
        PropertyChangeEvent propertyEvent = new PropertyChangeEvent(table,"tableCellEditor", new Object(), new Object());
        renamesListener.propertyChange(propertyEvent);

        table.editingStopped(new ChangeEvent(table));
        assertEquals(expectedStored, categoryStorage.getCategories(""));
    }

    @Test
    void renameFirstCategory() {
        table.getSelectionModel().setSelectionInterval(0,0);
        table.addPropertyChangeListener(renamesListener);

        table.editCellAt(0,0);
        table.setCellEditor(new DefaultCellEditor(new JTextField("Name10")));
        table.editingStopped(new ChangeEvent(table));

        expectedStored.set(0, new Category("Name10", 100, false));
        assertEquals(expectedStored, categoryStorage.getCategories(""));
    }

    @Test
    void renameLastCategory() {
        table.getSelectionModel().setSelectionInterval(3,3);
        table.addPropertyChangeListener(renamesListener);

        table.editCellAt(3,0);
        table.setCellEditor(new DefaultCellEditor(new JTextField("Test10")));
        table.editingStopped(new ChangeEvent(table));

        expectedStored.set(3, new Category("Test10", Float.NaN, false));
        assertEquals(expectedStored, categoryStorage.getCategories(""));
    }
}