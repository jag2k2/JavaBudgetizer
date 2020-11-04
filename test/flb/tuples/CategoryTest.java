package flb.tuples;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class CategoryTest {
    private Category category;

    @BeforeEach
    void setUp() {
        this.category = new Category("Name1", 100, false);
    }

    @Test
    void testToString() {
        String expected = "Name1 | 100.0 | false";
        String actual = category.toString();
        assertEquals(expected, actual);
    }

    @Test
    void testEquals() {
        Category categoryToCompare = new Category("Name1", 100, false);
        assertTrue(category.equals(categoryToCompare));

        categoryToCompare = new Category("Name2", 100, false);
        assertFalse(category.equals(categoryToCompare));

        categoryToCompare = new Category("Name1", 200, false);
        assertFalse(category.equals(categoryToCompare));

        categoryToCompare = new Category("Name1", 100, true);
        assertFalse(category.equals(categoryToCompare));
    }
}