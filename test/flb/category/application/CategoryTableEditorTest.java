package flb.category.application;

import org.junit.jupiter.api.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.util.*;
import flb.category.*;
import flb.database.*;
import static org.junit.jupiter.api.Assertions.*;

class CategoryTableEditorTest {
    private CategoryTable categoryTable;
    private CategoryTableEditor tableEditor;
    private TestDatabase database;
    private JTable table;
    private JTextField nameFilter;
    private CategoryStorage categoryStorage;
    private CategoryTableModel tableModel;
    private ArrayList<Category> expectedStored;
    private ArrayList<Category> expectedDisplayed;

    @BeforeEach
    void setUp() {
        this.database = new TestDatabase();
        database.connect();
        this.categoryStorage = new CategoryStorage(database);
        this.tableModel = new CategoryTableModel(categoryStorage.getCategories(""));
        this.table = new JTable(tableModel);
        this.nameFilter = new JTextField();
        this.categoryTable = new CategoryTable(table, tableModel);
        this.tableEditor = new CategoryTableEditor(categoryStorage, categoryTable, nameFilter);
        this.expectedStored = new ArrayList<>();
        this.expectedDisplayed = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void addingCategoryWithNoName() {
        nameFilter.setText("");
        expectedStored.add(new Category("Name1", 100, false));
        expectedStored.add(new Category("Name2", 200, true));
        expectedStored.add(new Category("Name3", 300, false));
        expectedStored.add(new Category("Test1", Float.NaN, false));
        expectedDisplayed = expectedStored;

        tableEditor.userAddCategory();

        assertEquals(expectedStored, categoryStorage.getCategories(""));
        assertEquals(expectedDisplayed, tableModel.getContents());
    }

    @Test
    void addingNewCategory() {
        nameFilter.setText("Test2");
        expectedStored.add(new Category("Name1", 100, false));
        expectedStored.add(new Category("Name2", 200, true));
        expectedStored.add(new Category("Name3", 300, false));
        expectedStored.add(new Category("Test1", Float.NaN, false));
        expectedStored.add(new Category("Test2", Float.NaN, false));
        expectedDisplayed = expectedStored;

        tableEditor.userAddCategory();

        assertEquals(expectedStored, categoryStorage.getCategories(""));
        assertEquals(expectedDisplayed, tableModel.getContents());
        assertEquals("", nameFilter.getText());
    }

    @Test
    void addingDuplicateCategory() {
        nameFilter.setText("Name1");
        expectedStored.add(new Category("Name1", 100, false));
        expectedStored.add(new Category("Name2", 200, true));
        expectedStored.add(new Category("Name3", 300, false));
        expectedStored.add(new Category("Test1", Float.NaN, false));
        expectedDisplayed.add(new Category("Name1", 100, false));

        tableEditor.userAddCategory();

        assertEquals(expectedStored, categoryStorage.getCategories(""));
        assertEquals(expectedDisplayed, tableModel.getContents());
        assertEquals("Name1", nameFilter.getText());
    }

    @Test
    void clearSelectedGoal() {
        nameFilter.setText("Name");
        table.getSelectionModel().setSelectionInterval(1,1);
        expectedStored.add(new Category("Name1", 100, false));
        expectedStored.add(new Category("Name2", Float.NaN, true));
        expectedStored.add(new Category("Name3", 300, false));
        expectedStored.add(new Category("Test1", Float.NaN, false));

        expectedDisplayed.add(new Category("Name1", 100, false));
        expectedDisplayed.add(new Category("Name2", Float.NaN, true));
        expectedDisplayed.add(new Category("Name3", 300, false));

        tableEditor.userClearCategoryGoal();

        assertEquals(expectedStored, categoryStorage.getCategories(""));
        assertEquals(expectedDisplayed, tableModel.getContents());
        assertEquals("Name", nameFilter.getText());
    }

    @Test
    void clearNoGoalSelected() {
        nameFilter.setText("");
        table.getSelectionModel().setSelectionInterval(-1,-1);
        expectedStored.add(new Category("Name1", 100, false));
        expectedStored.add(new Category("Name2", 200, true));
        expectedStored.add(new Category("Name3", 300, false));
        expectedStored.add(new Category("Test1", Float.NaN, false));
        expectedDisplayed = expectedStored;

        tableEditor.userClearCategoryGoal();

        assertEquals(expectedStored, categoryStorage.getCategories(""));
        assertEquals(expectedDisplayed, tableModel.getContents());
        assertEquals("", nameFilter.getText());
    }

    @Test
    void deleteSelectedGoalWithConfirm() {
        nameFilter.setText("Name");
        table.getSelectionModel().setSelectionInterval(1,1);
        expectedStored.add(new Category("Name1", 100, false));
        expectedStored.add(new Category("Name3", 300, false));
        expectedStored.add(new Category("Test1", Float.NaN, false));
        expectedDisplayed.add(new Category("Name1", 100, false));
        expectedDisplayed.add(new Category("Name3", 300, false));

        TableWillConfirmDelete categoryWillDelete = new TableWillConfirmDelete(categoryStorage, categoryTable, nameFilter);
        categoryWillDelete.userDeleteCategory(new JFrame());

        assertEquals(expectedStored, categoryStorage.getCategories(""));
        assertEquals(expectedDisplayed, tableModel.getContents());
        assertEquals("Name", nameFilter.getText());
    }

    static class TableWillConfirmDelete extends CategoryTableEditor {
        public TableWillConfirmDelete(CategoryStorage categoryStorage, CategoryTable categoryTable, JTextField nameFilter){
            super(categoryStorage, categoryTable, nameFilter);
        }

        @Override
        protected int getSelectionFromDialog(String categoryNameToDelete, JFrame frame){
            return JOptionPane.YES_OPTION;
        }
    }
    @Test
    void deleteSelectedGoalWithNoConfirm() {
        nameFilter.setText("Name");
        table.getSelectionModel().setSelectionInterval(1,1);
        expectedStored.add(new Category("Name1", 100, false));
        expectedStored.add(new Category("Name2", 200, true));
        expectedStored.add(new Category("Name3", 300, false));
        expectedStored.add(new Category("Test1", Float.NaN, false));
        expectedDisplayed.add(new Category("Name1", 100, false));
        expectedDisplayed.add(new Category("Name2", 200, true));
        expectedDisplayed.add(new Category("Name3", 300, false));

        TableWontConfirmDelete categoryWontDelete = new TableWontConfirmDelete(categoryStorage, categoryTable, nameFilter);
        categoryWontDelete.userDeleteCategory(new JFrame());

        assertEquals(expectedStored, categoryStorage.getCategories(""));
        assertEquals(expectedDisplayed, tableModel.getContents());
        assertEquals("Name", nameFilter.getText());
    }
    static class TableWontConfirmDelete extends CategoryTableEditor {
        public TableWontConfirmDelete(CategoryStorage categoryStorage, CategoryTable categoryTable, JTextField nameFilter){
            super(categoryStorage, categoryTable, nameFilter);
        }

        @Override
        protected int getSelectionFromDialog(String categoryNameToDelete, JFrame frame){
            return JOptionPane.NO_OPTION;
        }
    }

    @Test
    void filterCategories(){
        nameFilter.setText("Name");
        expectedDisplayed.add(new Category("Name1", 100, false));
        expectedDisplayed.add(new Category("Name2", 200, true));
        expectedDisplayed.add(new Category("Name3", 300, false));

        tableEditor.userFiltersCategories();

        assertEquals(expectedDisplayed, tableModel.getContents());
        assertEquals("Name", nameFilter.getText());
    }

    @Test
    void toggleExcludes() {
        nameFilter.setText("Name");
        table.getSelectionModel().setSelectionInterval(1,1);
        expectedStored.add(new Category("Name1", 100, false));
        expectedStored.add(new Category("Name2", 200, false));
        expectedStored.add(new Category("Name3", 300, false));
        expectedStored.add(new Category("Test1", Float.NaN, false));
        expectedDisplayed.add(new Category("Name1", 100, false));
        expectedDisplayed.add(new Category("Name2", 200, false));
        expectedDisplayed.add(new Category("Name3", 300, false));

        tableEditor.userEditsExcludes();

        assertEquals(expectedStored, categoryStorage.getCategories(""));
        assertEquals(expectedDisplayed, tableModel.getContents());
        assertEquals("Name", nameFilter.getText());
    }

    @Test
    void editCategoryAmount() {
        nameFilter.setText("Name");
        table.getSelectionModel().setSelectionInterval(1,1);
        tableModel.getContents().set(1, new Category("Name2", 500, false));

        expectedStored.add(new Category("Name1", 100, false));
        expectedStored.add(new Category("Name2", 500, true));
        expectedStored.add(new Category("Name3", 300, false));
        expectedStored.add(new Category("Test1", Float.NaN, false));
        expectedDisplayed.add(new Category("Name1", 100, false));
        expectedDisplayed.add(new Category("Name2", 500, true));
        expectedDisplayed.add(new Category("Name3", 300, false));

        tableEditor.userEditsGoalAmount();

        assertEquals(expectedStored, categoryStorage.getCategories(""));
        assertEquals(expectedDisplayed, tableModel.getContents());
        assertEquals("Name", nameFilter.getText());
    }

    @Test
    void getEditingCategory() {
        table.getSelectionModel().setSelectionInterval(1,1);
        table.editCellAt(1,0);
        System.out.println(table.isEditing());
        ArrayList<Category> expected = new ArrayList<>();
        expected.add(new Category("Name2", 200, true));

        ArrayList<Category> maybeCategory = tableEditor.getEditingCategory();

        assertEquals(expected, maybeCategory);
    }


    @Test
    void renameCategory() {
        nameFilter.setText("Name");
        table.getSelectionModel().setSelectionInterval(1,1);
        tableModel.getContents().set(1, new Category("Name20", 200, true));

        expectedStored.add(new Category("Name1", 100, false));
        expectedStored.add(new Category("Name20", 200, true));
        expectedStored.add(new Category("Name3", 300, false));
        expectedStored.add(new Category("Test1", Float.NaN, false));
        expectedDisplayed.add(new Category("Name1", 100, false));
        expectedDisplayed.add(new Category("Name20", 200, true));
        expectedDisplayed.add(new Category("Name3", 300, false));


        tableEditor.userRenamedCategory("Name2");

        assertEquals(expectedStored, categoryStorage.getCategories(""));
        assertEquals(expectedDisplayed, tableModel.getContents());
        assertEquals("Name", nameFilter.getText());
    }


}