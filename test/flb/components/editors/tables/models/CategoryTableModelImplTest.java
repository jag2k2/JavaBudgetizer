package flb.components.editors.tables.models;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import flb.databases.TestDatabase;
import flb.tuples.*;
import flb.util.*;

class CategoryTableModelImplTest {
    private CategoryTableModelImpl tableModel;
    private Maybe<Category> expected;
    private Maybe<Category> actual;

    @BeforeEach
    void setUp() {
        tableModel = new CategoryTableModelImpl();
        tableModel.updateCategories(TestDatabase.getTestCategories());
    }

    @Test
    void getCategory() {
                expected = new Maybe<>();
        actual = tableModel.getCategory(-1);
        assertEquals(expected, actual);

        int testCategoryCount = TestDatabase.getTestCategories().size();
        for(int i= 0; i < testCategoryCount; i++) {
            expected = new Maybe<>(TestDatabase.getTestCategories().get(i));
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
        Category expectedCategory = TestDatabase.getTestCategories().get(activeRow);

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
    void setValues() {
        expected = new Maybe<>(new Category("TestRename", 1000,false));

        tableModel.setValueAt("TestRename", 0, 0);

        actual = tableModel.getCategory(0);
        assertEquals(expected, actual);


        expected = new Maybe<>(new Category("Name2", 2000, true));

        tableModel.setValueAt(2000F, 1, 1);

        actual = tableModel.getCategory(1);
        assertEquals(expected, actual);
    }
}