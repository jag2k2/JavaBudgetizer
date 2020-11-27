package flb.tuples;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import flb.databases.*;

class CategoryTest {
    private Category category;
    private final String categoryName = "Income";

    @BeforeEach
    void setUp() {
        category = CategoryFactory.makeCategory(categoryName);
    }

    @Test
    void testToString() {
        String expected = "Income | [1000.0] | false";
        String actual = category.toString();
        assertEquals(expected, actual);
    }

    @Test
    void testEquals() {
        Category categoryToCompare = CategoryFactory.makeCategory(categoryName);
        assertEquals(categoryToCompare, category);

        categoryToCompare = CategoryFactory.makeCategoryWithNewName(categoryName, "Name2");
        assertNotEquals(categoryToCompare, category);

        categoryToCompare = CategoryFactory.makeCategoryWithNewAmount(categoryName, 200);
        assertNotEquals(categoryToCompare, category);

        categoryToCompare = CategoryFactory.makeCategoryWithNewExcluded(categoryName, true);
        assertNotEquals(categoryToCompare, category);
    }
}