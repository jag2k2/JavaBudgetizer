package flb.components.editors.tables.models;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import flb.datastores.TestDatabase;
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
        String actualName = (String)tableModel.getValueAt(0,0);
        assertEquals("Name1", actualName);

        float actualValue = (float)tableModel.getValueAt(1,1);
        assertEquals(200f, actualValue);

        boolean actualBool = (boolean)tableModel.getValueAt(2,2);
        assertFalse(actualBool);
    }

    @Test
    void setValues() {
        expected = new Maybe<>(new Category("TestRename", 100, false));

        tableModel.setValueAt("TestRename", 0, 0);

        actual = tableModel.getCategory(0);
        assertEquals(expected, actual);


        expected = new Maybe<>(new Category("Name2", 2000, true));

        tableModel.setValueAt(2000F, 1, 1);

        actual = tableModel.getCategory(1);
        assertEquals(expected, actual);
    }
}