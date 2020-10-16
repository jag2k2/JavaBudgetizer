package flb.category.application;

import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.*;
import flb.category.*;
import flb.database.*;
import static org.junit.jupiter.api.Assertions.*;

class CategoryTableEditorTest {
    private CategoryTable categoryTable;
    private CategoryTableEditor tableEditor;
    private TestDatabase database;
    private JTable table;
    private CategoryStorage categoryStorage;
    private CategoryTableModel tableModel;
    private ArrayList<Category> expectedStored;

    @BeforeEach
    void setUp() {
        this.database = new TestDatabase();
        database.connect();
        this.categoryStorage = new CategoryStorage(database);
        this.tableModel = new CategoryTableModel(categoryStorage.getCategories(""));
        this.table = new JTable(tableModel);
        this.categoryTable = new CategoryTable(table, tableModel);
        this.tableEditor = new CategoryTableEditor(categoryStorage, categoryTable);

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
    void categoryAddable() {
        assertFalse(tableEditor.categoryNameAddable(""));
        assertFalse(tableEditor.categoryNameAddable("Name1"));
        assertTrue(tableEditor.categoryNameAddable("Name0"));
    }

    @Test
    void addingCategoryWithNoName() {
        tableEditor.userAddCategory("");

        assertEquals(expectedStored, categoryStorage.getCategories(""));
    }

    @Test
    void addingNewCategory() {
        expectedStored.add(new Category("Test2", Float.NaN, false));

        tableEditor.userAddCategory("Test2");

        assertEquals(expectedStored, categoryStorage.getCategories(""));
    }

    @Test
    void addingDuplicateCategory() {
        tableEditor.userAddCategory("Name1");

        assertEquals(expectedStored, categoryStorage.getCategories(""));
    }

    @Test
    void clearSelectedGoal() {
        table.getSelectionModel().setSelectionInterval(1,1);
        expectedStored.set(1, new Category("Name2", Float.NaN, true));

        tableEditor.userClearSelectedCategoryGoal();

        assertEquals(expectedStored, categoryStorage.getCategories(""));
    }

    @Test
    void clearNoGoalSelected() {
        table.getSelectionModel().setSelectionInterval(-1,-1);

        tableEditor.userClearSelectedCategoryGoal();

        assertEquals(expectedStored, categoryStorage.getCategories(""));
    }

    @Test
    void deleteSelectedGoalWithConfirm() {
        table.getSelectionModel().setSelectionInterval(1,1);
        expectedStored.remove(1);

        TableWillConfirmDelete categoryWillDelete = new TableWillConfirmDelete(categoryStorage, categoryTable);
        categoryWillDelete.userDeleteSelectedCategory(new JFrame());

        assertEquals(expectedStored, categoryStorage.getCategories(""));
    }

    static class TableWillConfirmDelete extends CategoryTableEditor {
        public TableWillConfirmDelete(CategoryStorage categoryStorage, CategoryTable categoryTable){
            super(categoryStorage, categoryTable);
        }

        @Override
        protected int getSelectionFromDialog(String categoryNameToDelete, JFrame frame){
            return JOptionPane.YES_OPTION;
        }
    }
    @Test
    void deleteSelectedGoalWithNoConfirm() {
        table.getSelectionModel().setSelectionInterval(1,1);

        TableWontConfirmDelete categoryWontDelete = new TableWontConfirmDelete(categoryStorage, categoryTable);
        categoryWontDelete.userDeleteSelectedCategory(new JFrame());

        assertEquals(expectedStored, categoryStorage.getCategories(""));
    }
    static class TableWontConfirmDelete extends CategoryTableEditor {
        public TableWontConfirmDelete(CategoryStorage categoryStorage, CategoryTable categoryTable){
            super(categoryStorage, categoryTable);
        }

        @Override
        protected int getSelectionFromDialog(String categoryNameToDelete, JFrame frame){
            return JOptionPane.NO_OPTION;
        }
    }

    @Test
    void toggleExcludes() {
        table.getSelectionModel().setSelectionInterval(1,1);
        expectedStored.set(1, new Category("Name2", 200, false));

        tableEditor.userEditsSelectedExcludes();

        assertEquals(expectedStored, categoryStorage.getCategories(""));
    }

    @Test
    void editCategoryAmount() {
        table.getSelectionModel().setSelectionInterval(1,1);
        tableModel.getContents().set(1, new Category("Name2", 500, true));
        expectedStored.set(1, new Category("Name2", 500, true));

        tableEditor.userEditsSelectedGoalAmount();

        assertEquals(expectedStored, categoryStorage.getCategories(""));
    }

    @Test
    void getEditingCategory() {
        table.getSelectionModel().setSelectionInterval(1,1);
        table.editCellAt(1,0);
        ArrayList<Category> expected = new ArrayList<>();
        expected.add(new Category("Name2", 200, true));

        ArrayList<Category> maybeCategory = tableEditor.getEditingCategory();

        assertEquals(expected, maybeCategory);
    }


    @Test
    void renameCategory() {
        table.getSelectionModel().setSelectionInterval(1,1);
        tableModel.getContents().set(1, new Category("Name20", 200, true));
        expectedStored.set(1, new Category("Name20", 200, true));

        tableEditor.userRenamedCategory("Name2");

        assertEquals(expectedStored, categoryStorage.getCategories(""));
    }

    @Test
    void refresh(){
        expectedStored.remove(3);

        tableEditor.refresh("Name");

        assertEquals(0, table.getSelectionModel().getSelectedIndices().length);
        assertEquals(expectedStored, tableModel.getContents());
    }

    @Test
    void refreshKeepSelections() {
        table.getSelectionModel().setSelectionInterval(2,2);
        expectedStored.remove(3);

        tableEditor.refreshAndKeepSelection("Name");

        assertEquals(2, table.getSelectionModel().getSelectedIndices()[0]);
        assertEquals(expectedStored, tableModel.getContents());
    }
}