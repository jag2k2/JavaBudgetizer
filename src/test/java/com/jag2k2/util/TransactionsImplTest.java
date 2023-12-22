package com.jag2k2.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import com.jag2k2.databases.DebitFactory;
import com.jag2k2.databases.DebitListFactory;
import com.jag2k2.tuples.BankingTransaction;
import java.util.Calendar;

class TransactionsImplTest {
    private Transactions<BankingTransaction> bankingTransactions;
    private WhichMonth whichMonth;

    @BeforeEach
    void setUp() {
        whichMonth = new WhichMonth(2020, Calendar.OCTOBER);
    }

    @Test
    void transactionsCanReportSize() {
        bankingTransactions = DebitListFactory.makeDefaultTransactions();
        int expected = DebitFactory.getDefaultRefs().length;
        assertEquals(expected, bankingTransactions.size());
    }

    @Test
    void transactionsCanBeAddedOnto() {
        bankingTransactions = DebitListFactory.makeDefaultTransactions();
        BankingTransaction banking0 = DebitFactory.makeNewTransaction(whichMonth);
        bankingTransactions.add(banking0);
        int expected = DebitFactory.getDefaultRefs().length + 1;
        assertEquals(expected, bankingTransactions.size());
    }

    @Test
    void transactionsCanBeCleared() {
        bankingTransactions = DebitListFactory.makeDefaultTransactions();
        bankingTransactions.clear();
        assertEquals(0, bankingTransactions.size());
    }

    @Test
    void canGetTransactionByIndex() {
        bankingTransactions = DebitListFactory.makeDefaultTransactions();
        BankingTransaction expected = DebitFactory.makeDefaultTransaction("123");
        assertEquals(expected, bankingTransactions.get(0));
    }

    @Test
    void iterator() {

    }
}