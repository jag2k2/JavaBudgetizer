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

class UserEditsExcludesListenerTest {
    private TestDatabase database;
    private CategoryStoreEditor categoryStoreEditor;
    private JTextField nameFilter;
    private ArrayList<Category> expectedStored;
    private JTable table;
    private TableCellEditor cellEditor;
    private JCheckBox editorComponent;

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
        CategoryExcludeEditor excludeEditor = new CategoryTableEditorImp(categoryStoreEditor, categoryTable);
        cellEditor = table.getDefaultEditor(Boolean.class);
        cellEditor.addCellEditorListener(new UserEditsExcludesListener(excludeEditor, nameFilter));
        editorComponent = (JCheckBox) cellEditor.getTableCellEditorComponent(table, true, false, 0, 2);
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
        assertEquals(expectedStored, categoryStoreEditor.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }

    @Test
    void disableEnabledCategory() {
        nameFilter.setText("Name");
        table.getSelectionModel().setSelectionInterval(1,1);

        editorComponent.setSelected(false);
        cellEditor.stopCellEditing();

        expectedStored.set(1, new Category("Name2", 200, false));
        assertEquals(expectedStored, categoryStoreEditor.getCategories(""));
        assertEquals("Name", nameFilter.getText());
    }
}