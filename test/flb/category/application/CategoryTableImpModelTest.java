package flb.category.application;

import flb.category.*;
import flb.util.*;
import org.junit.jupiter.api.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTableImpModelTest {
    private CategoryTableModelImp tableModel;
    private Maybe<Category> expected;
    private Maybe<Category> actual;

    @BeforeEach
    void setUp() {
        ArrayList<Category> modelContents = new ArrayList<>();
        modelContents.add(new Category("Name1", 100, false));
        modelContents.add(new Category("Name2", 200, true));
        modelContents.add(new Category("Name3", 300, false));
        modelContents.add(new Category("Test1", Float.NaN, false));
        tableModel = new CategoryTableModelImp();
        tableModel.updateCategories(modelContents);
    }

    @Test
    void getCategory() {
        expected = new Maybe<>();
        actual = tableModel.getCategory(-1);
        assertEquals(expected, actual);

        expected = new Maybe<>(new Category("Name1", 100, false));
        actual = tableModel.getCategory(0);
        assertEquals(expected, actual);

        expected = new Maybe<>(new Category("Name2", 200, true));
        actual = tableModel.getCategory(1);
        assertEquals(expected, actual);

        expected = new Maybe<>(new Category("Test1", Float.NaN, false));
        actual = tableModel.getCategory(3);
        assertEquals(expected, actual);

        expected = new Maybe<>();
        actual = tableModel.getCategory(4);
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