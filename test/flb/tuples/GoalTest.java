package flb.tuples;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import flb.util.WhichMonth;
import java.util.Calendar;

class GoalTest {
    private Goal goal;

    @BeforeEach
    void setUp() {
        WhichMonth whichMonth = new WhichMonth(2020, Calendar.OCTOBER);
        Category category = new Category("Name1", 100, false);
        this.goal = new Goal(whichMonth, category, 200);
    }

    @Test
    void testToString() {
        String expected = "2020-October | Name1 | 100.0 | false | 200.0";
        String actual = goal.toString();
        assertEquals(expected, actual);
    }

    @Test
    void testEquals() {
        WhichMonth whichMonth = new WhichMonth(2020, Calendar.OCTOBER);
        Category category = new Category("Name1", 100, false);
        Goal goalToCompare = new Goal(whichMonth, category, 200);

        assertEquals(goalToCompare, goal);

        whichMonth = new WhichMonth(2020, Calendar.NOVEMBER);
        category = new Category("Name1", 100, false);
        goalToCompare = new Goal(whichMonth, category, 200);

        assertNotEquals(goalToCompare, goal);

        whichMonth = new WhichMonth(2020, Calendar.OCTOBER);
        category = new Category("Name1", 200, false);
        goalToCompare = new Goal(whichMonth, category, 200);

        assertNotEquals(goalToCompare, goal);

        whichMonth = new WhichMonth(2020, Calendar.OCTOBER);
        category = new Category("Name1", 100, false);
        goalToCompare = new Goal(whichMonth, category, 300);

        assertNotEquals(goalToCompare, goal);
    }
}