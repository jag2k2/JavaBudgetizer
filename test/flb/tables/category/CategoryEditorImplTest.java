package flb.tables.category;

import static org.junit.jupiter.api.Assertions.*;
import flb.database.interfaces.CategoryStore;
import flb.tables.category.interfaces.CategoryTableAutomator;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.*;
import flb.util.*;
import flb.tuples.*;
import flb.database.*;

class CategoryEditorImplTest {
    private CategoryEditorImpl categoryEditor;
    private CategoryTableAutomator tableAutomator;
    private TestDatabase database;
    private CategoryStore categoryStore;
    private ArrayList<Category> expected;

    @BeforeEach
    void setUp() {
        this.database = new TestDatabase();
        database.connect();
        this.categoryStore = new CategoryStoreImpl(database);
        this.categoryEditor = new CategoryEditorImpl(categoryStore);
        this.tableAutomator = categoryEditor.getTableAutomator();
        categoryEditor.refreshAndClearSelection("");
        this.expected = TestDatabase.getTestCategories();
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void categoryAddable() {
        assertFalse(categoryEditor.categoryNameAddable(""));
        assertFalse(categoryEditor.categoryNameAddable("Name1"));
        assertTrue(categoryEditor.categoryNameAddable("Name0"));
    }

    @Test
    void addingCategoryWithNoName() {
        categoryEditor.userAddCategory("");

        assertEquals(expected, categoryStore.getCategories(""));
    }

    @Test
    void addingNewCategory() {
        expected.add(new Category("Test2", Float.NaN, false));

        categoryEditor.userAddCategory("Test2");

        assertEquals(expected, categoryStore.getCategories(""));
    }

    @Test
    void addingDuplicateCategory() {
        categoryEditor.userAddCategory("Name1");

        assertEquals(expected, categoryStore.getCategories(""));
    }

    @Test
    void clearSelectedGoal() {
        tableAutomator.setSelectedRow(1);
        expected.set(1, new Category("Name2", Float.NaN, true));

        categoryEditor.userClearSelectedGoalAmount();

        assertEquals(expected, categoryStore.getCategories(""));
    }

    @Test
    void clearNoGoalSelected() {
        tableAutomator.setSelectedRow(-1);

        categoryEditor.userClearSelectedGoalAmount();

        assertEquals(expected, categoryStore.getCategories(""));
    }

    @Test
    void deleteSelectedGoalWithConfirm() {
        categoryEditor = new CategoryEditorNoDialog(categoryStore, true);
        categoryEditor.refreshAndClearSelection("");
        tableAutomator = categoryEditor.getTableAutomator();
        tableAutomator.setSelectedRow(1);

        categoryEditor.userDeleteSelectedCategory(new JFrame());

        expected.remove(1);
        assertEquals(expected, categoryStore.getCategories(""));
    }

    @Test
    void deleteSelectedGoalWithNoConfirm() {
        tableAutomator.setSelectedRow(1);

        categoryEditor = new CategoryEditorNoDialog(categoryStore, false);
        categoryEditor.userDeleteSelectedCategory(new JFrame());

        assertEquals(expected, categoryStore.getCategories(""));
    }

    @Test
    void toggleExcludes() {
        tableAutomator.setSelectedRow(1);
        expected.set(1, new Category("Name2", 200, false));

        categoryEditor.userEditsSelectedExcludes();

        assertEquals(expected, categoryStore.getCategories(""));
    }

    @Test
    void editCategoryAmount() {
        tableAutomator.setSelectedRow(1);
        expected.set(1, new Category("Name2", 500, true));

        tableAutomator.editCellAt(1,1);
        tableAutomator.setEditorGoal(500);
        categoryEditor.userEditsSelectedGoalAmount();

        assertEquals(expected, categoryStore.getCategories(""));
    }

    @Test
    void getSelectedCategory() {
        tableAutomator.setSelectedRow(1);
        Maybe<Category> expected = new Maybe<>(TestDatabase.getTestCategories().get(1));

        Maybe<Category> editingCategory = categoryEditor.getSelectedCategory();

        assertEquals(expected, editingCategory);
    }


    @Test
    void renameCategory() {
        tableAutomator.setSelectedRow(1);
        expected.set(1, new Category("Name20", 200, true));

        tableAutomator.editCellAt(1,0);
        tableAutomator.setEditorName("Name20");
        categoryEditor.userRenamedCategory("Name2");

        assertEquals(expected, categoryStore.getCategories(""));
    }

    @Test
    void refresh(){
        expected.remove(4);
        expected.remove(3);

        categoryEditor.refreshAndClearSelection("Name");

        assertEquals(-1, tableAutomator.getSelectedRow());
        assertEquals(expected, tableAutomator.getContents());
    }

    @Test
    void refreshKeepSelections() {
        tableAutomator.setSelectedRow(2);
        expected.remove(4);
        expected.remove(3);

        categoryEditor.refreshAndKeepSelection("Name");

        assertEquals(2, tableAutomator.getSelectedRow());
        assertEquals(expected, tableAutomator.getContents());
    }
}