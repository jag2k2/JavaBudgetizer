package flb.category.application.listeners;

import flb.category.*;
import flb.category.application.*;
import flb.database.*;
import org.junit.jupiter.api.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserEditsGoalAmountListenerTest {
    private TestDatabase database;
    private CategoryStorage categoryStorage;
    private JTable table;
    private ArrayList<Category> expectedStored;
    private JTextField nameFilter;
    private TableCellEditor cellEditor;
    private JTextField editorComponent;

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
        cellEditor = table.getDefaultEditor(Float.class);
        tableModel.addTableModelListener(new UserEditsGoalAmountListener(tableEditor, nameFilter));
        editorComponent = (JTextField) cellEditor.getTableCellEditorComponent(table, "", false, 0, 1);

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
    void editCategoryGoal() {
        nameFilter.setText("Name");
        table.getSelectionModel().setSelectionInterval(0,0);
        table.editCellAt(0,1);

        editorComponent.setText("200.0");
        cellEditor.stopCellEditing();

        expectedStored.set(0, new Category("Name1", 200, false));
        assertEquals(expectedStored, categoryStorage.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }
}