package com.jag2k2.listeners;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import com.jag2k2.databases.*;
import com.jag2k2.components.editor.category.CategoryEditorImpl;
import com.jag2k2.tuples.*;
import com.jag2k2.datastores.*;
import javax.swing.*;
import java.util.*;

class UserAddsCategoryListenerTest {
    private JButton testButton;
    private ArrayList<Category> expected;
    private TestDatabase database;
    private CategoryStore categoryStore;
    private CategoryEditorImpl categoryEditor;

    @BeforeEach
    void setUp() {
        database = new TestDatabase();
        database.connect();
        categoryStore = new DataStoreImpl(database);
        categoryEditor = new CategoryEditorImpl(categoryStore);
        testButton = new JButton();

        expected = CategoryListFactory.makeDefaultCategories();

        testButton.addActionListener(new UserAddsCategoryListener(categoryEditor));
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void categoryAdded() {
        String nameFilterText = "Test2";
        expected.add(CategoryFactory.makeIncludedCategory(nameFilterText));
        categoryEditor.setNameFilter(nameFilterText);

        testButton.doClick();

        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals("", categoryEditor.getNameFilter());
    }

    @Test
    void emptyNameNotAdded() {
        String nameFilterText = "";
        categoryEditor.setNameFilter(nameFilterText);

        testButton.doClick();

        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals(nameFilterText, categoryEditor.getNameFilter());
    }

    @Test
    void duplicateNameNotAdded() {
        String nameFilterText = "Name2";
        categoryEditor.setNameFilter(nameFilterText);

        testButton.doClick();

        assertEquals(expected, categoryStore.getCategories(""));
        assertEquals(nameFilterText, categoryEditor.getNameFilter());
    }
}