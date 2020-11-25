package flb.util;

import flb.databases.TestDatabase;
import flb.tuples.BankingTransaction;
import flb.tuples.CreditTransaction;
import flb.tuples.Transaction;
import org.junit.jupiter.api.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

class TransactionsImplTest {
    private Transactions<Transaction> transactions1;
    private Transactions<BankingTransaction> transactions2;
    private Transactions<CreditTransaction> transactions3;

    @BeforeEach
    void setUp() {

    }

    @Test
    void assignments() {
        transactions1 = new TransactionsImpl<>();
        Transaction banking0 = TestDatabase.getTestBankingTransactions().get(0);
        transactions1.add(banking0);

        transactions2 = new TransactionsImpl<>();
        BankingTransaction banking1 = new BankingTransaction("1", new GregorianCalendar(2020, Calendar.OCTOBER, 5),
                "", 100F, "", 100F);
        transactions2.add(banking1);

        transactions3 = new TransactionsImpl<>();
        CreditTransaction credit0 = new CreditTransaction("1", new GregorianCalendar(2020, Calendar.OCTOBER, 5),
                "", 100F, "", "");
        transactions3.add(credit0);

        //transactions1 = transactions3;

    }

    @Test
    void iterator() {

    }
}