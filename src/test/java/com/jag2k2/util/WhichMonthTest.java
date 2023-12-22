package com.jag2k2.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class WhichMonthTest {

    @Test
    void testToString() {
        WhichMonth whichMonth = new WhichMonth(2020, 0);
        assertEquals("2020-January", whichMonth.toString());
    }

    @Test
    void testSQLString() {
        WhichMonth whichMonth = new WhichMonth(2020, 0);
        assertEquals("2020-01", whichMonth.toSQLString());

        whichMonth = new WhichMonth(2020, 11);
        assertEquals("2020-12", whichMonth.toSQLString());
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

    @Test
    void setMonth() {
        WhichMonth expected = new WhichMonth(2020,5);
        WhichMonth monthTest = new WhichMonth(2020,0);

        monthTest.setMonth(5);

        assertEquals(expected, monthTest);
    }

    @Test
    void setYear() {
        WhichMonth expected = new WhichMonth(2025,0);
        WhichMonth monthTest = new WhichMonth(2020,0);

        monthTest.setYear(2025);

        assertEquals(expected, monthTest);
    }
}
