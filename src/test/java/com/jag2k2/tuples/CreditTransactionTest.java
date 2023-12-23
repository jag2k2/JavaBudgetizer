package com.jag2k2.tuples;

import static org.junit.jupiter.api.Assertions.*;

import com.jag2k2.databases.CreditFactory;
import org.junit.jupiter.api.*;
import java.util.*;

class CreditTransactionTest {
    private CreditTransaction creditTransaction;

    @BeforeEach
    void setup() {
        creditTransaction = CreditFactory.makeDefaultTransaction("3589048");
    }

    @Test
    void testToString() {
        String actual = creditTransaction.toString();
        String expected = "3589048 | 2020-10-25 | Shell | -20.0 | Name3 | ";
        assertEquals(expected, actual);
    }

    @Test
    void testEquals() {
        CreditTransaction transactionToCompare = CreditFactory.makeDefaultTransaction("3589048");
        assertEquals(transactionToCompare, creditTransaction);

        transactionToCompare = CreditFactory.makeTransactionWithNewRef("3589048", "11111");
        assertNotEquals(transactionToCompare, creditTransaction);

        Calendar date = new GregorianCalendar(2020, Calendar.OCTOBER, 1);
        transactionToCompare = CreditFactory.makeTransactionWithNewDate("3589048", date);
        assertNotEquals(transactionToCompare, creditTransaction);

        transactionToCompare = CreditFactory.makeTransactionWithNewDescription("3589048", "NewDescription");
        assertNotEquals(transactionToCompare, creditTransaction);

        transactionToCompare = CreditFactory.makeTransactionWithNewAmount("3589048", 42);
        assertNotEquals(transactionToCompare, creditTransaction);

        transactionToCompare = CreditFactory.makeTransactionWithNewPayGroup("3589048", "TestPayGroup");
        assertNotEquals(transactionToCompare, creditTransaction);
    }
}