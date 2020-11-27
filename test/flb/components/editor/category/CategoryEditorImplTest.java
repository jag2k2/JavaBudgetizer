package flb.components.editor.category;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import flb.databases.*;
import flb.datastores.*;
import javax.swing.*;
import java.util.*;
import flb.util.*;
import flb.tuples.*;

class CategoryEditorImplTest {
    private CategoryEditorImpl categoryEditor;
    private CategoryTableTester tableAutomator;
    private TestDatabase database;
    private CategoryStore categoryStore;
    private List<Category> expected;

    @BeforeEach
    void setUp() {
        database = new TestDatabase();
        database.connect();
        categoryStore = new CategoryStoreImpl(database);
        categoryEditor = new CategoryEditorImpl(categoryStore);
        categoryStore.addStoreChangeObserver(categoryEditor);
        categoryEditor.update();

        tableAutomator = categoryEditor.getTableTester();
        expected = CategoryListFactory.makeDefaultCategories();
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void categoryAddable() {
        assertFalse(categoryEditor.categoryNameAddable(""));

        String nameExists = CategoryListFactory.makeDefaultCategories().get(0).getName();
        assertFalse(categoryEditor.categoryNameAddable(nameExists));

        assertTrue(categoryEditor.categoryNameAddable("Name0"));
    }

    @Test
    void addingCategoryWithNoName() {
        categoryEditor.setNameFilter("");
        categoryEditor.userAddCategory();

        assertEquals(expected, categoryStore.getCategories(""));
    }

    @Test
    void addingNewCategory() {
        String nameFilterText = "Test2";
        categoryEditor.setNameFilter(nameFilterText);
        expected.add(CategoryFactory.makeIncludedCategory(nameFilterText));

        categoryEditor.userAddCategory();

        assertEquals(expected, categoryStore.getCategories(""));
    }

    @Test
    void addingDuplicateCategory() {
        String duplicateName = CategoryListFactory.makeDefaultCategories().get(0).getName();
        categoryEditor.setNameFilter(duplicateName);
        categoryEditor.userAddCategory();

        assertEquals(expected, categoryStore.getCategories(""));
    }

    @Test
    void clearSelectedGoal() {
        int rowToClear = 1;
        categoryEditor.userClearGoalAmount(rowToClear);

        String selectedCategory = tableAutomator.getContents().get(rowToClear).getName();
        expected = CategoryListFactory.makeDefaultCategoriesWithOneCleared(selectedCategory);
        assertEquals(expected, categoryStore.getCategories(""));
    }

    @Test
    void clearNoGoalSelected() {
        categoryEditor.userClearGoalAmount(-1);

        assertEquals(expected, categoryStore.getCategories(""));
    }

    @Test
    void deleteSelectedGoalWithConfirmAndCategoryUnused() {
        categoryEditor = new CategoryEditorNoDialog(categoryStore, true);
        categoryEditor.update();
        tableAutomator = categoryEditor.getTableTester();

        categoryEditor.userDeleteCategory(3, new JFrame());

        expected.remove(3);
        assertEquals(expected, categoryStore.getCategories(""));
    }

    @Test
    void deleteSelectedGoalWithConfirmButCategoryUsed() {
        categoryEditor = new CategoryEditorNoDialog(categoryStore, true);
        tableAutomator = categoryEditor.getTableTester();

        categoryEditor.userDeleteCategory(1, new JFrame());

        assertEquals(expected, categoryStore.getCategories(""));
    }

    @Test
    void deleteSelectedGoalWithNoConfirm() {
        tableAutomator.setSelectedRow(1);

        categoryEditor = new CategoryEditorNoDialog(categoryStore, false);
        categoryEditor.userDeleteCategory(1, new JFrame());

        assertEquals(expected, categoryStore.getCategories(""));
    }

    @Test
    void toggleExcludes() {
        int selectedRow = 1;
        tableAutomator.setSelectedRow(selectedRow);

        categoryEditor.userEditsSelectedExcludes();

        String selectedCategory = tableAutomator.getContents().get(selectedRow).getName();
        expected = CategoryListFactory.makeDefaultCategoriesWithOneExcludesChanged(selectedCategory, false);
        assertEquals(expected, categoryStore.getCategories(""));
    }

    @Test
    void editCategoryAmount() {
        int selectedRow = 1;
        float newAmount = 500;
        tableAutomator.setSelectedRow(selectedRow);

        tableAutomator.editCellAt(selectedRow,1);
        tableAutomator.setEditorGoal(newAmount);
        categoryEditor.UpdateSelectedGoalAmount();

        String categorySelected = tableAutomator.getContents().get(selectedRow).getName();
        expected = CategoryListFactory.makeDefaultCategoriesWithOneAmountChanged(categorySelected, newAmount);
        assertEquals(expected, categoryStore.getCategories(""));
    }

    @Test
    void getSelectedCategory() {
        tableAutomator.setSelectedRow(1);
        Maybe<Category> expected = new Maybe<>(CategoryListFactory.makeDefaultCategories().get(1));

        Maybe<Category> editingCategory = categoryEditor.getSelectedCategory();

        assertEquals(expected, editingCategory);
    }


    @Test
    void renameCategory() {
        int selectedRow = 1;
        String oldName = tableAutomator.getContents().get(selectedRow).getName();
        String newName = "Name20";

        tableAutomator.setSelectedRow(selectedRow);
        tableAutomator.editCellAt(selectedRow,0);
        tableAutomator.setEditorName(newName);
        categoryEditor.userRenamedCategory(oldName);


        expected = CategoryListFactory.makeDefaultCategoriesWithOneRenamed(oldName, newName);
        assertEquals(expected, categoryStore.getCategories(""));
    }

    @Test
    void refresh(){
        String filterText = "Name";

        categoryEditor.setNameFilter(filterText);
        categoryEditor.update();

        expected = CategoryListFactory.makeFilteredDefaultCategories(filterText);
        assertEquals(expected, tableAutomator.getContents());
        assertEquals(-1, tableAutomator.getSelectedRow());
    }

    @Test
    void refreshKeepSelections() {
        String filterText = "Name";
        categoryEditor.setNameFilter(filterText);

        tableAutomator.setSelectedRow(2);
        expected = new ArrayList<>();
        for(Category category : CategoryListFactory.makeDefaultCategories()){
            if(category.getName().contains(filterText)){
                expected.add(category);
            }
        }

        categoryEditor.updateAndKeepSelection();

        assertEquals(2, tableAutomator.getSelectedRow());
        assertEquals(expected, tableAutomator.getContents());
    }
}