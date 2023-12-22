package com.jag2k2.listeners;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import com.jag2k2.databases.*;
import com.jag2k2.components.editor.category.CategoryEditorImpl;
import com.jag2k2.components.editor.category.CategoryTableTester;
import com.jag2k2.tuples.*;
import com.jag2k2.datastores.*;
import java.util.*;

class UserEditsExcludesListenerTest {
    private ArrayList<Category> expected;
    private CategoryTableTester tableAutomator;
    private CategoryEditorImpl categoryEditor;
    private TestDatabase database;
    private CategoryStore categoryStore;

    @BeforeEach
    void setUp() {
        database = new TestDatabase();
        database.connect();
        categoryStore = new DataStoreImpl(database);
        categoryEditor = new CategoryEditorImpl(categoryStore);
        categoryEditor.update();
        tableAutomator = categoryEditor.getTableTester();

        expected = CategoryListFactory.makeDefaultCategories();
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void enableDisabledCategory() {
        int activeRow = 0;

        String nameFilterText = "Name";
        categoryEditor.setNameFilter(nameFilterText);
        tableAutomator.setSelectedRow(activeRow);

        tableAutomator.toggleSelectedExcludes();

        Category category = CategoryListFactory.makeDefaultCategories().get(1);
        category.toggleExcludes();
        expected.set(1, category);
        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals(nameFilterText, categoryEditor.getNameFilter());
    }

    @Test
    void disableEnabledCategory() {
        String nameFilterText = "Name";
        categoryEditor.setNameFilter(nameFilterText);
        tableAutomator.setSelectedRow(0);

        String categorySelected = "";
        for(Category category : categoryEditor.getSelectedCategory()){
            categorySelected = category.getName();
        }

        tableAutomator.toggleSelectedExcludes();

        expected = CategoryListFactory.makeDefaultCategoriesWithOneExcludesChanged(categorySelected, false);
        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals(nameFilterText, categoryEditor.getNameFilter());
    }
}