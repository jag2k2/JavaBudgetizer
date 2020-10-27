package flb.tuples;

import org.junit.jupiter.api.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class BankingTransactionTest {
    private BankingTransaction bankingTransaction;
    private Calendar date;

    @BeforeEach
    void setup() {
        this.date = new GregorianCalendar(2020,10,3);
        this.bankingTransaction = new BankingTransaction("EE6A3",
                date, "Amazon", 100F,
                "Misc", 1000F);
    }

    @Test
    void testToString() {
        String result = bankingTransaction.toString();
        String expected = "EE6A3 2020-10-3 Amazon 100.0 Misc 1000.0";
        assertEquals(expected, result);
    }

    @Test
    void testEquals() {
        BankingTransaction compareTransaction = new BankingTransaction("EE6A3",
                date, "Amazon", 100F,
                "Misc", 1000F);
        assertEquals(compareTransaction, bankingTransaction);

        compareTransaction = new BankingTransaction("EE6A4",
                date, "Amazon", 100F,
                "Misc", 1000F);
        assertNotEquals(compareTransaction, bankingTransaction);

        compareTransaction = new BankingTransaction("EE6A3",
                date, "HEB", 100F,
                "Misc", 1000F);
        assertNotEquals(compareTransaction, bankingTransaction);

        compareTransaction = new BankingTransaction("EE6A3",
                date, "Amazon", 200F,
                "Misc", 1000F);
        assertNotEquals(compareTransaction, bankingTransaction);

        compareTransaction = new BankingTransaction("EE6A3",
                date, "Amazon", 100F,
                "Groceries", 1000F);
        assertNotEquals(compareTransaction, bankingTransaction);

        compareTransaction = new BankingTransaction("EE6A3",
                date, "Amazon", 100F,
                "Misc", 2000F);
        assertNotEquals(compareTransaction, bankingTransaction);

    }
}