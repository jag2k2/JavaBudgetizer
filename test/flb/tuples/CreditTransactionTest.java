package flb.tuples;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.*;

class CreditTransactionTest {
    private CreditTransaction creditTransaction;
    private Calendar date;

    @BeforeEach
    void setup() {
        date = new GregorianCalendar(2020,Calendar.NOVEMBER,3);
        creditTransaction = new CreditTransaction("EE6A3",
                date, "Amazon", 100F, "Misc");
    }

    @Test
    void testToString() {
        String actual = creditTransaction.toString();
        String expected = "EE6A3 | 2020-11-03 | Amazon | 100.0 | Misc";
        assertEquals(expected, actual);
    }

    @Test
    void testEquals() {
        CreditTransaction transactionToCompare = new CreditTransaction("EE6A3",
                date, "Amazon", 100F, "Misc");
        assertEquals(transactionToCompare, creditTransaction);

        transactionToCompare = new CreditTransaction("EE6A4",
                date, "Amazon", 100F, "Misc");
        assertNotEquals(transactionToCompare, creditTransaction);

        transactionToCompare = new CreditTransaction("EE6A3",
                date, "HEB", 100F, "Misc");
        assertNotEquals(transactionToCompare, creditTransaction);

        transactionToCompare = new CreditTransaction("EE6A3",
                date, "Amazon", 200F, "Misc");
        assertNotEquals(transactionToCompare, creditTransaction);

        transactionToCompare = new CreditTransaction("EE6A3",
                date, "Amazon", 100F, "Groceries");
        assertNotEquals(transactionToCompare, creditTransaction);
    }
}