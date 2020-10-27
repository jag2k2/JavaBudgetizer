package flb.tuples;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class CreditTransactionTest {
    private CreditTransaction creditTransaction;
    private Calendar date;

    @BeforeEach
    void setup() {
        this.date = new GregorianCalendar(2020,10,3);
        this.creditTransaction = new CreditTransaction("EE6A3",
                date, "Amazon", 100F, "Misc");
    }

    @Test
    void testToString() {
        String result = creditTransaction.toString();
        String expected = "EE6A3 2020-10-3 Amazon 100.0 Misc";
        assertEquals(expected, result);
    }

    @Test
    void testEquals() {
        CreditTransaction compareTransaction = new CreditTransaction("EE6A3",
                date, "Amazon", 100F, "Misc");
        assertEquals(compareTransaction, creditTransaction);

        compareTransaction = new CreditTransaction("EE6A4",
                date, "Amazon", 100F, "Misc");
        assertNotEquals(compareTransaction, creditTransaction);

        compareTransaction = new CreditTransaction("EE6A3",
                date, "HEB", 100F, "Misc");
        assertNotEquals(compareTransaction, creditTransaction);

        compareTransaction = new CreditTransaction("EE6A3",
                date, "Amazon", 200F, "Misc");
        assertNotEquals(compareTransaction, creditTransaction);

        compareTransaction = new CreditTransaction("EE6A3",
                date, "Amazon", 100F, "Groceries");
        assertNotEquals(compareTransaction, creditTransaction);
    }
}