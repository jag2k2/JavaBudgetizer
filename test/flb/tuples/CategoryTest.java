package flb.tuples;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import flb.databases.*;

class CategoryTest {
    private Category category;
    private final int categoryIndex = 0;

    @BeforeEach
    void setUp() {
        category = CategoryFactory.makeCategory(categoryIndex);
    }

    @Test
    void testToString() {
        String expected = "Income | [1000.0] | false";
        String actual = category.toString();
        assertEquals(expected, actual);
    }

    @Test
    void testEquals() {
        Category categoryToCompare = CategoryFactory.makeCategory(categoryIndex);
        assertEquals(categoryToCompare, category);

        categoryToCompare = CategoryFactory.makeCategoryWithNewName(categoryIndex, "Name2");
        assertNotEquals(categoryToCompare, category);

        categoryToCompare = CategoryFactory.makeCategoryWithNewAmount(categoryIndex, 200);
        assertNotEquals(categoryToCompare, category);

        categoryToCompare = CategoryFactory.makeCategoryWithNewExcluded(categoryIndex, true);
        assertNotEquals(categoryToCompare, category);
    }
}