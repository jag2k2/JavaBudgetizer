package com.jag2k2.tuples;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import com.jag2k2.databases.DebitFactory;
import java.util.*;

class BankingTransactionTest {
    private BankingTransaction bankingTransaction;

    @BeforeEach
    void setup() {
        bankingTransaction = DebitFactory.makeDefaultTransaction("123");
    }

    @Test
    void testToString() {
        String actual = bankingTransaction.toString();
        String expected = "123 | 2020-10-25 | Amazon | -50.0 | Test1::sub2 | 1000.0";
        assertEquals(expected, actual);
    }

    @Test
    void testEquals() {
        BankingTransaction transactionToCompare = DebitFactory.makeDefaultTransaction("123");
        assertEquals(transactionToCompare, bankingTransaction);

        transactionToCompare = DebitFactory.makeTransactionWithNewRef("123", "ABC");
        assertNotEquals(transactionToCompare, bankingTransaction);

        Calendar date = new GregorianCalendar(2020, Calendar.NOVEMBER, 7);
        transactionToCompare = DebitFactory.makeTransactionWithNewDate("123", date);
        assertNotEquals(transactionToCompare, bankingTransaction);

        transactionToCompare = DebitFactory.makeTransactionWithNewDescription("123", "Blockbuster");
        assertNotEquals(transactionToCompare, bankingTransaction);

        transactionToCompare = DebitFactory.makeTransactionWithNewAmount("123", 42);
        assertNotEquals(transactionToCompare, bankingTransaction);

        transactionToCompare = DebitFactory.makeTransactionWithCategory("123", "TestCategory");
        assertNotEquals(transactionToCompare, bankingTransaction);

        transactionToCompare = DebitFactory.makeTransactionWithNewBalance("123", 2000);
        assertNotEquals(transactionToCompare, bankingTransaction);
    }
}