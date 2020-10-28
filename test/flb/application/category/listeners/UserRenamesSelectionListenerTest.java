package flb.application.category.listeners;

import flb.tables.category.CategoryTable;
import flb.tables.category.CategoryTableEditorImp;
import flb.tables.category.CategoryTableImp;
import flb.tables.category.CategoryTableModelImp;
import flb.tuples.*;
import flb.database.*;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.*;
import javax.swing.table.*;
import static org.junit.jupiter.api.Assertions.*;

class UserRenamesSelectionListenerTest {
    private TestDatabase database;
    private CategoryStoreEditor categoryStoreEditor;
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
        expectedStored.add(new Category("Test1::sub1", Float.NaN, false));
        expectedStored.add(new Category("Test1::sub2", 500, true));

        this.nameFilter = new JTextField();
        this.database = new TestDatabase();
        database.connect();
        this.categoryStoreEditor = new CategoryStoreEditorImp(database);
        CategoryTableModelImp tableModel = new CategoryTableModelImp();
        tableModel.updateCategories(expectedStored);
        this.table = new JTable(tableModel);
        CategoryTable categoryTable = new CategoryTableImp(table, tableModel);
        CategoryNameEditor nameEditor = new CategoryTableEditorImp(categoryStoreEditor, categoryTable);
        table.addPropertyChangeListener(new UserRenamesSelectionListener(nameEditor, nameFilter));
        cellEditor = table.getDefaultEditor(String.class);
        editorComponent = (JTextField) cellEditor.getTableCellEditorComponent(table, "", false, 0, 0);
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
        editorComponent.setText("Name10");
        cellEditor.stopCellEditing();

        expectedStored.set(0, new Category("Name10", 100, false));
        assertEquals(expectedStored, categoryStoreEditor.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }

    @Test
    void renameLastCategory() {
        nameFilter.setText("Name");
        table.getSelectionModel().setSelectionInterval(3,3);

        table.editCellAt(3,0);
        editorComponent.setText("Test10");
        cellEditor.stopCellEditing();

        expectedStored.set(3, new Category("Test10", Float.NaN, false));
        assertEquals(expectedStored, categoryStoreEditor.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }
}