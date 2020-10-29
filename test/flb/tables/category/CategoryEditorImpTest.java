package flb.tables.category;

import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.*;
import flb.util.*;
import flb.tuples.*;
import flb.database.*;
import static org.junit.jupiter.api.Assertions.*;

class CategoryEditorImpTest {
    private CategoryEditorImp tableEditor;
    private CategoryTable categoryTable;
    private CategoryTableAutomator tableAutomator;
    private TestDatabase database;
    private CategoryStore categoryStore;
    private ArrayList<Category> expectedStored;

    @BeforeEach
    void setUp() {
        CategoryTableImp categoryTableImp = new CategoryTableImp();
        this.tableAutomator = categoryTableImp;
        this.categoryTable = categoryTableImp;

        this.expectedStored = new ArrayList<>();
        expectedStored.add(new Category("Name1", 100, false));
        expectedStored.add(new Category("Name2", 200, true));
        expectedStored.add(new Category("Name3", 300, false));
        expectedStored.add(new Category("Test1::sub1", Float.NaN, false));
        expectedStored.add(new Category("Test1::sub2", 500, true));

        this.database = new TestDatabase();
        database.connect();
        this.categoryStore = new CategoryStoreImpl(database);
        categoryTable.displayAndClearSelection(expectedStored);
        this.tableEditor = new CategoryEditorImp(categoryStore, categoryTable);
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

        assertEquals(expectedStored, categoryStore.getCategories(""));
    }

    @Test
    void addingNewCategory() {
        expectedStored.add(new Category("Test2", Float.NaN, false));

        tableEditor.userAddCategory("Test2");

        assertEquals(expectedStored, categoryStore.getCategories(""));
    }

    @Test
    void addingDuplicateCategory() {
        tableEditor.userAddCategory("Name1");

        assertEquals(expectedStored, categoryStore.getCategories(""));
    }

    @Test
    void clearSelectedGoal() {
        tableAutomator.setSelectedRow(1);
        expectedStored.set(1, new Category("Name2", Float.NaN, true));

        tableEditor.userClearSelectedGoalAmount();

        assertEquals(expectedStored, categoryStore.getCategories(""));
    }

    @Test
    void clearNoGoalSelected() {
        tableAutomator.setSelectedRow(-1);

        tableEditor.userClearSelectedGoalAmount();

        assertEquals(expectedStored, categoryStore.getCategories(""));
    }

    @Test
    void deleteSelectedGoalWithConfirm() {
        tableAutomator.setSelectedRow(1);

        tableEditor = new CategoryEditorNoDialog(categoryStore, categoryTable, true);
        tableEditor.userDeleteSelectedCategory(new JFrame());

        expectedStored.remove(1);
        assertEquals(expectedStored, categoryStore.getCategories(""));
    }

    @Test
    void deleteSelectedGoalWithNoConfirm() {
        tableAutomator.setSelectedRow(1);

        tableEditor = new CategoryEditorNoDialog(categoryStore, categoryTable, false);
        tableEditor.userDeleteSelectedCategory(new JFrame());

        assertEquals(expectedStored, categoryStore.getCategories(""));
    }

    @Test
    void toggleExcludes() {
        tableAutomator.setSelectedRow(1);
        expectedStored.set(1, new Category("Name2", 200, false));

        tableEditor.userEditsSelectedExcludes();

        assertEquals(expectedStored, categoryStore.getCategories(""));
    }

    @Test
    void editCategoryAmount() {
        tableAutomator.setSelectedRow(1);
        expectedStored.set(1, new Category("Name2", 500, true));

        tableEditor.userEditsSelectedGoalAmount();

        assertEquals(expectedStored, categoryStore.getCategories(""));
    }

    @Test
    void getSelectedCategory() {
        tableAutomator.setSelectedRow(1);
        Maybe<Category> expected = new Maybe<>(new Category("Name2", 200, true));

        Maybe<Category> editingCategory = tableEditor.getSelectedCategory();

        assertEquals(expected, editingCategory);
    }


    @Test
    void renameCategory() {
        tableAutomator.setSelectedRow(1);
        expectedStored.set(1, new Category("Name20", 200, true));

        tableEditor.userRenamedCategory("Name2");

        assertEquals(expectedStored, categoryStore.getCategories(""));
    }

    @Test
    void refresh(){
        expectedStored.remove(4);
        expectedStored.remove(3);

        tableEditor.refreshAndClearSelection("Name");

        assertEquals(-1, tableAutomator.getSelectedRow());
        assertEquals(expectedStored, tableAutomator.getContents());
    }

    @Test
    void refreshKeepSelections() {
        tableAutomator.setSelectedRow(2);
        expectedStored.remove(4);
        expectedStored.remove(3);

        tableEditor.refreshAndKeepSelection("Name");

        assertEquals(2, tableAutomator.getSelectedRow());
        assertEquals(expectedStored, tableAutomator.getContents());
    }
}