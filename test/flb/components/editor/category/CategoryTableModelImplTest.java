package flb.components.editor.category;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import flb.databases.*;
import flb.tuples.*;
import flb.util.*;

class CategoryTableModelImplTest {
    private CategoryTableModelImpl tableModel;
    private Maybe<Category> expected;
    private Maybe<Category> actual;

    @BeforeEach
    void setUp() {
        tableModel = new CategoryTableModelImpl();
        tableModel.updateCategories(CategoryListFactory.makeDefaultCategories());
    }

    @Test
    void getCategory() {
                expected = new Maybe<>();
        actual = tableModel.getCategory(-1);
        assertEquals(expected, actual);

        int testCategoryCount = CategoryListFactory.makeDefaultCategories().size();
        for(int i= 0; i < testCategoryCount; i++) {
            expected = new Maybe<>(CategoryListFactory.makeDefaultCategories().get(i));
            actual = tableModel.getCategory(i);
            assertEquals(expected, actual);
        }

        expected = new Maybe<>();
        actual = tableModel.getCategory(testCategoryCount);
        assertEquals(expected, actual);
    }

    @Test
    void getValues() {
        int activeRow = 0;
        Category expectedCategory = CategoryListFactory.makeDefaultCategories().get(activeRow);

        String expectedName = expectedCategory.getName();
        String actualName = (String)tableModel.getValueAt(activeRow,0);
        assertEquals(expectedName, actualName);

        float expectedAmount = expectedCategory.getDefaultGoalDisplay();
        float actualValue = (float)tableModel.getValueAt(activeRow,1);
        assertEquals(expectedAmount, actualValue);

        boolean expectedExcludes = expectedCategory.getExclude();
        boolean actualExcludes = (boolean)tableModel.getValueAt(activeRow,2);
        assertEquals(expectedExcludes, actualExcludes);
    }

    @Test
    void setNameValue() {
        int rowToSet = 0;
        String newName = "TestRename";

        tableModel.setValueAt(newName, rowToSet, 0);

        expected = new Maybe<>(CategoryFactory.makeCategoryWithNewName(rowToSet, newName));
        assertEquals(expected, tableModel.getCategory(rowToSet));



    }

    @Test
    void setAmountValue() {
        int rowToSet = 1;
        float newAmount = 2000;

        tableModel.setValueAt(newAmount, rowToSet, 1);

        expected = new Maybe<>(CategoryFactory.makeCategoryWithNewAmount(rowToSet, newAmount));
        assertEquals(expected, tableModel.getCategory(1));

    }
}