package com.jag2k2.listeners;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import com.jag2k2.databases.*;
import com.jag2k2.components.editor.category.CategoryEditorImpl;
import com.jag2k2.components.editor.category.CategoryTableTester;
import com.jag2k2.tuples.*;
import com.jag2k2.datastores.*;


import java.util.*;

class UserFiltersCategoriesListenerTest {
    private ArrayList<Category> expected;
    private CategoryTableTester tableAutomator;
    private CategoryEditorImpl categoryEditor;
    private TestDatabase database;

    @BeforeEach
    void setUp() {
        database = new TestDatabase();
        database.connect();
        CategoryStore categoryStore = new DataStoreImpl(database);
        categoryEditor = new CategoryEditorImpl(categoryStore);
        tableAutomator = categoryEditor.getTableTester();

        expected = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void removeUpdate() {
        String filterString = "Name";
        categoryEditor.setNameFilter(filterString);
        for (Category category : CategoryListFactory.makeDefaultCategories()){
            if(category.getName().contains(filterString)){
                expected.add(category);
            }
        }

        assertEquals(expected, tableAutomator.getContents());
    }
}