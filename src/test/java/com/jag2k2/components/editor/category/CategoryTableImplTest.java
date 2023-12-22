package com.jag2k2.components.editor.category;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import com.jag2k2.databases.CategoryListFactory;
import com.jag2k2.tuples.*;
import com.jag2k2.util.*;
import java.util.*;

class CategoryTableImplTest {
    private CategoryTable categoryTable;
    private CategoryTableTester tableAutomator;
    private ArrayList<Category> expectedDisplay;

    @BeforeEach
    void setUp() {
        CategoryTableImpl categoryTableImpl = new CategoryTableImpl();
        this.categoryTable = categoryTableImpl;
        this.tableAutomator = categoryTableImpl;

        expectedDisplay = CategoryListFactory.makeDefaultCategories();
        categoryTable.displayAndClearSelection(expectedDisplay);
    }

    @Test
    void getSelectedCategory() {
        tableAutomator.setSelectedRow(-1);
        Maybe<Category> expected = new Maybe<>();
        assertEquals(expected, categoryTable.getSelectedCategory());

        int testCategoryCount = CategoryListFactory.makeDefaultCategories().size();
        for (int i = 0; i < testCategoryCount; i++) {
            tableAutomator.setSelectedRow(i);
            expected = new Maybe<>(CategoryListFactory.makeDefaultCategories().get(i));
            assertEquals(expected, categoryTable.getSelectedCategory());
        }

        tableAutomator.setSelectedRow(testCategoryCount);
        expected = new Maybe<>();
        assertEquals(expected, categoryTable.getSelectedCategory());
    }

    @Test
    void displayAndClearSelection() {
        tableAutomator.setSelectedRow(1);

        categoryTable.displayAndClearSelection(expectedDisplay);

        assertEquals(expectedDisplay, tableAutomator.getContents());
        assertEquals(-1, tableAutomator.getSelectedRow());


        expectedDisplay = new ArrayList<>();

        categoryTable.displayAndClearSelection(expectedDisplay);

        assertEquals(expectedDisplay, tableAutomator.getContents());
        assertEquals(-1, tableAutomator.getSelectedRow());
    }

    @Test
    void displayAndKeepSelection() {
        tableAutomator.setSelectedRow(1);

        categoryTable.displayAndKeepSelection(expectedDisplay);

        assertEquals(expectedDisplay, tableAutomator.getContents());
        assertEquals(1, tableAutomator.getSelectedRow());
    }
}