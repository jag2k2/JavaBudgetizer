package flb.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;



class WhichMonthTest {

    @Test
    void testToString() {
        WhichMonth whichMonth = new WhichMonth(2020, 0);
        assertEquals("2020, January", whichMonth.toString());
    }

    @Test
    void testEquals() {
        WhichMonth expected = new WhichMonth(2020,1);
        WhichMonth monthTest = new WhichMonth(2020,1);
        assertEquals(expected, monthTest);

        monthTest = new WhichMonth(2020, 2);
        assertNotEquals(expected, monthTest);
    }

    @Test
    void incrementMonth() {
        WhichMonth expected = new WhichMonth(2020,1);
        WhichMonth monthTest = new WhichMonth(2020,0);

        monthTest.incrementMonth();
        assertEquals(expected, monthTest);


        expected = new WhichMonth(2021,0);
        monthTest = new WhichMonth(2020,11);

        monthTest.incrementMonth();
        assertEquals(expected, monthTest);
    }

    @Test
    void decrementMonth() {
        WhichMonth expected = new WhichMonth(2020,0);
        WhichMonth monthTest = new WhichMonth(2020,1);

        monthTest.decrementMonth();
        assertEquals(expected, monthTest);


        expected = new WhichMonth(2020,11);
        monthTest = new WhichMonth(2021,0);

        monthTest.decrementMonth();
        assertEquals(expected, monthTest);
    }
}
