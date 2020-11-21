package flb.tuples;

import org.junit.jupiter.api.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class BankingTransactionTest {
    private BankingTransaction bankingTransaction;
    private Calendar date;

    @BeforeEach
    void setup() {
        this.date = new GregorianCalendar(2020,Calendar.OCTOBER,3);
        this.bankingTransaction = new BankingTransaction("EE6A3",
                date, "Amazon", 100F,
                "Misc", 1000F);
    }

    @Test
    void testToString() {
        String actual = bankingTransaction.toString();
        String expected = "EE6A3 | 2020-10-3 | Amazon | 100.0 | Misc | 1000.0";
        assertEquals(expected, actual);
    }

    @Test
    void testEquals() {
        BankingTransaction transactionToCompare = new BankingTransaction("EE6A3",
                date, "Amazon", 100F,
                "Misc", 1000F);
        assertEquals(transactionToCompare, bankingTransaction);

        transactionToCompare = new BankingTransaction("EE6A4",
                date, "Amazon", 100F,
                "Misc", 1000F);
        assertNotEquals(transactionToCompare, bankingTransaction);

        transactionToCompare = new BankingTransaction("EE6A3",
                date, "HEB", 100F,
                "Misc", 1000F);
        assertNotEquals(transactionToCompare, bankingTransaction);

        transactionToCompare = new BankingTransaction("EE6A3",
                date, "Amazon", 200F,
                "Misc", 1000F);
        assertNotEquals(transactionToCompare, bankingTransaction);

        transactionToCompare = new BankingTransaction("EE6A3",
                date, "Amazon", 100F,
                "Groceries", 1000F);
        assertNotEquals(transactionToCompare, bankingTransaction);

        transactionToCompare = new BankingTransaction("EE6A3",
                date, "Amazon", 100F,
                "Misc", 2000F);
        assertNotEquals(transactionToCompare, bankingTransaction);
    }
}