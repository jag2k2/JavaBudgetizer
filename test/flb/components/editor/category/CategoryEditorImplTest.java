package flb.components.editor.category;

import static org.junit.jupiter.api.Assertions.*;

import flb.databases.TestDatabase;
import org.junit.jupiter.api.*;
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
        expected = TestDatabase.getTestCategories();
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void categoryAddable() {
        assertFalse(categoryEditor.categoryNameAddable(""));

        String nameExists = TestDatabase.getTestCategories().get(0).getName();
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
        expected.add(new Category(nameFilterText, false));

        categoryEditor.userAddCategory();

        assertEquals(expected, categoryStore.getCategories(""));
    }

    @Test
    void addingDuplicateCategory() {
        String duplicateName = TestDatabase.getTestCategories().get(0).getName();
        categoryEditor.setNameFilter(duplicateName);
        categoryEditor.userAddCategory();

        assertEquals(expected, categoryStore.getCategories(""));
    }

    @Test
    void clearSelectedGoal() {
        expected.set(1, new Category("Name2", true));

        categoryEditor.userClearGoalAmount(1);

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
        tableAutomator.setSelectedRow(1);
        expected.set(1, new Category("Name2", 200,false));

        categoryEditor.userEditsSelectedExcludes();

        assertEquals(expected, categoryStore.getCategories(""));
    }

    @Test
    void editCategoryAmount() {
        tableAutomator.setSelectedRow(1);
        expected.set(1, new Category("Name2", 500,true));

        tableAutomator.editCellAt(1,1);
        tableAutomator.setEditorGoal(500);
        categoryEditor.UpdateSelectedGoalAmount();

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
        String filterText = "Name";
        categoryEditor.setNameFilter(filterText);
        expected = new ArrayList<>();
        for(Category category : TestDatabase.getTestCategories()){
            if(category.getName().contains(filterText)){
                expected.add(category);
            }
        }

        categoryEditor.update();

        assertEquals(-1, tableAutomator.getSelectedRow());
        assertEquals(expected, tableAutomator.getContents());
    }

    @Test
    void refreshKeepSelections() {
        String filterText = "Name";
        categoryEditor.setNameFilter(filterText);

        tableAutomator.setSelectedRow(2);
        expected = new ArrayList<>();
        for(Category category : TestDatabase.getTestCategories()){
            if(category.getName().contains(filterText)){
                expected.add(category);
            }
        }

        categoryEditor.updateAndKeepSelection();

        assertEquals(2, tableAutomator.getSelectedRow());
        assertEquals(expected, tableAutomator.getContents());
    }
}