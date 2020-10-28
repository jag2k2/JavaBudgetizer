package flb.tables;

import flb.tables.category.CategoryTable;
import flb.tables.category.CategoryTableEditorImp;
import flb.tables.category.CategoryTableImp;
import flb.tables.category.CategoryTableModelImp;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.*;
import flb.util.*;
import flb.tuples.*;
import flb.database.*;
import static org.junit.jupiter.api.Assertions.*;

class CategoryTableEditorImpTest {
    private CategoryTableEditorImp tableEditor;
    private CategoryTable categoryTable;
    private TestDatabase database;
    private JTable table;
    private CategoryStoreEditor categoryStoreEditor;
    private CategoryTableModelImp tableModel;
    private ArrayList<Category> expectedStored;

    @BeforeEach
    void setUp() {
        this.expectedStored = new ArrayList<>();
        expectedStored.add(new Category("Name1", 100, false));
        expectedStored.add(new Category("Name2", 200, true));
        expectedStored.add(new Category("Name3", 300, false));
        expectedStored.add(new Category("Test1::sub1", Float.NaN, false));
        expectedStored.add(new Category("Test1::sub2", 500, true));

        this.database = new TestDatabase();
        database.connect();
        this.categoryStoreEditor = new CategoryStoreEditorImp(database);
        this.tableModel = new CategoryTableModelImp();
        tableModel.updateCategories(expectedStored);
        this.table = new JTable(tableModel);
        this.categoryTable = new CategoryTableImp(table, tableModel);
        this.tableEditor = new CategoryTableEditorImp(categoryStoreEditor, categoryTable);
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void categoryAddable() {
        assertFalse(tableEditor.categoryNameAddable(""));
        assertFalse(tableEditor.categoryNameAddable("Name1"));
        assertTrue(tableEditor.categoryNameAddable("Name0"));
    }

    @Test
    void addingCategoryWithNoName() {
        tableEditor.userAddCategory("");

        assertEquals(expectedStored, categoryStoreEditor.getCategories(""));
    }

    @Test
    void addingNewCategory() {
        expectedStored.add(new Category("Test2", Float.NaN, false));

        tableEditor.userAddCategory("Test2");

        assertEquals(expectedStored, categoryStoreEditor.getCategories(""));
    }

    @Test
    void addingDuplicateCategory() {
        tableEditor.userAddCategory("Name1");

        assertEquals(expectedStored, categoryStoreEditor.getCategories(""));
    }

    @Test
    void clearSelectedGoal() {
        table.getSelectionModel().setSelectionInterval(1,1);
        expectedStored.set(1, new Category("Name2", Float.NaN, true));

        tableEditor.userClearSelectedGoalAmount();

        assertEquals(expectedStored, categoryStoreEditor.getCategories(""));
    }

    @Test
    void clearNoGoalSelected() {
        table.getSelectionModel().setSelectionInterval(-1,-1);

        tableEditor.userClearSelectedGoalAmount();

        assertEquals(expectedStored, categoryStoreEditor.getCategories(""));
    }

    @Test
    void deleteSelectedGoalWithConfirm() {
        table.getSelectionModel().setSelectionInterval(1,1);

        tableEditor = new TableEditorNoDialog(categoryStoreEditor, categoryTable, true);
        tableEditor.userDeleteSelectedCategory(new JFrame());

        expectedStored.remove(1);
        assertEquals(expectedStored, categoryStoreEditor.getCategories(""));
    }

    @Test
    void deleteSelectedGoalWithNoConfirm() {
        table.getSelectionModel().setSelectionInterval(1,1);

        tableEditor = new TableEditorNoDialog(categoryStoreEditor, categoryTable, false);
        tableEditor.userDeleteSelectedCategory(new JFrame());

        assertEquals(expectedStored, categoryStoreEditor.getCategories(""));
    }

    @Test
    void toggleExcludes() {
        table.getSelectionModel().setSelectionInterval(1,1);
        expectedStored.set(1, new Category("Name2", 200, false));

        tableEditor.userEditsSelectedExcludes();

        assertEquals(expectedStored, categoryStoreEditor.getCategories(""));
    }

    @Test
    void editCategoryAmount() {
        table.getSelectionModel().setSelectionInterval(1,1);
        expectedStored.set(1, new Category("Name2", 500, true));

        tableEditor.userEditsSelectedGoalAmount();

        assertEquals(expectedStored, categoryStoreEditor.getCategories(""));
    }

    @Test
    void getSelectedCategory() {
        table.getSelectionModel().setSelectionInterval(1,1);
        Maybe<Category> expected = new Maybe<>(new Category("Name2", 200, true));

        Maybe<Category> editingCategory = tableEditor.getSelectedCategory();

        assertEquals(expected, editingCategory);
    }


    @Test
    void renameCategory() {
        table.getSelectionModel().setSelectionInterval(1,1);
        expectedStored.set(1, new Category("Name20", 200, true));

        tableEditor.userRenamedCategory("Name2");

        assertEquals(expectedStored, categoryStoreEditor.getCategories(""));
    }

    @Test
    void refresh(){
        expectedStored.remove(4);
        expectedStored.remove(3);

        tableEditor.refreshAndClearSelection("Name");

        assertEquals(0, table.getSelectionModel().getSelectedIndices().length);
        assertEquals(expectedStored, tableModel.getContents());
    }

    @Test
    void refreshKeepSelections() {
        table.getSelectionModel().setSelectionInterval(2,2);
        expectedStored.remove(4);
        expectedStored.remove(3);

        tableEditor.refreshAndKeepSelection("Name");

        assertEquals(2, table.getSelectionModel().getSelectedIndices()[0]);
        assertEquals(expectedStored, tableModel.getContents());
    }
}