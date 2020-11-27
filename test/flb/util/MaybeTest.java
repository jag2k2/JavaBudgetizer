package flb.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import flb.databases.*;
import flb.tuples.*;


class MaybeTest {
    private Maybe<Category> definitelySomething;
    private Maybe<Category> nothing;
    private Category testCategory;

    @BeforeEach
    void setUp() {
        testCategory = CategoryFactory.makeCategory("Income");
        definitelySomething = new Maybe<>(testCategory);
        nothing = new Maybe<>();
    }

    @Test
    void testToString() {
        String expected = "[" + testCategory + "]";
        assertEquals(expected, definitelySomething.toString());

        expected = "[]";
        assertEquals(expected, nothing.toString());
    }

    @Test
    void testEquals() {
        Maybe<Category> maybeToCompare = new Maybe<>(testCategory);
        assertEquals(maybeToCompare, definitelySomething);

        assertNotEquals(maybeToCompare, nothing);

        maybeToCompare = new Maybe<>();
        assertEquals(maybeToCompare, nothing);
    }
}