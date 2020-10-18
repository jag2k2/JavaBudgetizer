package flb.category.application.listeners;

import flb.category.application.*;
import flb.category.*;
import flb.database.*;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.*;
import javax.swing.table.*;
import static org.junit.jupiter.api.Assertions.*;

class UserEditsExcludesListenerTest {
    private TestDatabase database;
    private CategoryStorage categoryStorage;
    private JTextField nameFilter;
    private ArrayList<Category> expectedStored;
    private JTable table;
    private TableCellEditor cellEditor;
    private JCheckBox editorComponent;

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
        cellEditor = table.getDefaultEditor(Boolean.class);
        cellEditor.addCellEditorListener(new UserEditsExcludesListener(tableEditor, nameFilter));
        editorComponent = (JCheckBox) cellEditor.getTableCellEditorComponent(table, true, false, 0, 2);

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
    void enableDisabledCategory() {
        nameFilter.setText("Name");
        table.getSelectionModel().setSelectionInterval(0,0);

        editorComponent.setSelected(true);
        cellEditor.stopCellEditing();

        expectedStored.set(0, new Category("Name1", 100, true));
        assertEquals(expectedStored, categoryStorage.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }

    @Test
    void disableEnabledCategory() {
        nameFilter.setText("Name");
        table.getSelectionModel().setSelectionInterval(1,1);

        editorComponent.setSelected(false);
        cellEditor.stopCellEditing();

        expectedStored.set(1, new Category("Name2", 200, false));
        assertEquals(expectedStored, categoryStorage.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }
}