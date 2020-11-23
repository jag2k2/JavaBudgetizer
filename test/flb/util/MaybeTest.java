package flb.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import flb.tuples.*;


class MaybeTest {
    private Maybe<Category> definitelySomething;
    private Maybe<Category> nothing;

    @BeforeEach
    void setUp() {
        definitelySomething = new Maybe<>(new Category("Name1", 100, false));
        nothing = new Maybe<>();
    }

    @Test
    void iterator() {
    }

    @Test
    void testToString() {
        String expected = "[Name1 | [100.0] | false]";
        assertEquals(expected, definitelySomething.toString());

        expected = "[]";
        assertEquals(expected, nothing.toString());
    }

    @Test
    void testEquals() {
        Maybe<Category> maybeToCompare = new Maybe<>(new Category("Name1", 100, false));
        assertEquals(maybeToCompare, definitelySomething);

        assertNotEquals(maybeToCompare, nothing);

        maybeToCompare = new Maybe<>();
        assertEquals(maybeToCompare, nothing);
    }
}