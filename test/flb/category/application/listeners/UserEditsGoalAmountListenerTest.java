package flb.category.application.listeners;

import flb.category.*;
import flb.category.application.*;
import flb.category.application.CategoryTableEditor;
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
        CategoryTable categoryTable = new CategoryTable(table, tableModel);
        CategoryAmountEditor amountEditor = new CategoryTableEditor(categoryStorage, categoryTable);
        cellEditor = table.getDefaultEditor(Float.class);
        tableModel.addTableModelListener(new UserEditsGoalAmountListener(amountEditor, nameFilter));
        editorComponent = (JTextField) cellEditor.getTableCellEditorComponent(table, "", false, 0, 1);
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