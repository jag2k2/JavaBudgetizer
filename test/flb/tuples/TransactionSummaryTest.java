package flb.tuples;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TransactionSummaryTest {
    private TransactionSummary transactionSummary;

    @BeforeEach
    void setUp() {
        this.transactionSummary = new TransactionSummary("Name1", 100, 115);
    }

    @Test
    void testToString() {
        String expected = "Name1 | 100.0 | 115.0";
        assertEquals(expected, transactionSummary.toString());
    }

    @Test
    void testEquals() {
        TransactionSummary summaryToCompare = new TransactionSummary("Name1", 100, 115);
        assertEquals(transactionSummary, summaryToCompare);


        summaryToCompare = new TransactionSummary("Name2", 100, 115);
        assertNotEquals(transactionSummary, summaryToCompare);

        summaryToCompare = new TransactionSummary("Name1", 200, 115);
        assertNotEquals(transactionSummary, summaryToCompare);

        summaryToCompare = new TransactionSummary("Name1", 100, 215);
        assertNotEquals(transactionSummary, summaryToCompare);
    }

    @Test
    void testEqualsWithNaN() {
        transactionSummary = new TransactionSummary("Name1", 100, Float.NaN);
        TransactionSummary summaryToCompare = new TransactionSummary("Name1", 100, Float.NaN);

        assertEquals(transactionSummary, summaryToCompare);

        transactionSummary = new TransactionSummary("Name1", Float.NaN, 100);
        summaryToCompare = new TransactionSummary("Name1", Float.NaN, 100);

        assertEquals(transactionSummary, summaryToCompare);
    }
}