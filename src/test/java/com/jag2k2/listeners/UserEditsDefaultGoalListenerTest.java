package com.jag2k2.listeners;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.*;
import com.jag2k2.databases.*;
import com.jag2k2.components.editor.category.CategoryEditorImpl;
import com.jag2k2.components.editor.category.CategoryTableTester;
import com.jag2k2.tuples.*;
import com.jag2k2.datastores.*;
import java.util.*;

class UserEditsDefaultGoalListenerTest {
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
        tableAutomator = categoryEditor.getTableTester();

        expected = CategoryListFactory.makeDefaultCategories();
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void editCategoryGoal() {
        int activeRow = 0;
        float newDefaultGoal = 543;

        String nameFilterText = "Name";
        categoryEditor.setNameFilter(nameFilterText);
        tableAutomator.setSelectedRow(activeRow);

        tableAutomator.editCellAt(activeRow,1);
        tableAutomator.setEditorGoal(newDefaultGoal);

        Category category = CategoryListFactory.makeDefaultCategories().get(1);
        category.setDefaultGoal(newDefaultGoal);
        expected.set(1, category);
        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals(nameFilterText, categoryEditor.getNameFilter());
    }
}