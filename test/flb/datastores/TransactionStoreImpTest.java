package flb.datastores;

import flb.util.WhichMonth;
import flb.tuples.*;
import org.junit.jupiter.api.*;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class TransactionStoreImpTest {
    private TransactionStore transactionStore;
    private TestDatabase dataBase;
    private WhichMonth whichMonth;

    @BeforeEach
    void setUp(){
        dataBase = new TestDatabase();
        dataBase.connect();
        transactionStore = new TransactionStoreImp(dataBase);
        this.whichMonth = new WhichMonth(2020, Calendar.OCTOBER);
    }

    @AfterEach
    void tearDown() {
        dataBase.close();
    }

    @Test
    void getBankingTransactions() {
        ArrayList<BankingTransaction> expected = TestDatabase.getTestBankingTransactions();

        assertEquals(expected, transactionStore.getBankingTransactions(whichMonth));
    }

    @Test
    void getCreditTransactions() {
        ArrayList<CreditTransaction> expected = TestDatabase.getTestCreditTransactions();

        assertEquals(expected, transactionStore.getCreditTransactions(whichMonth));
    }

    @Test
    void updateBankingTransaction() {
        ArrayList<BankingTransaction> expected = TestDatabase.getTestBankingTransactions();
        BankingTransaction bankingTransaction = expected.get(0);
        Calendar date1 = new GregorianCalendar(2020, Calendar.OCTOBER, 25);
        expected.set(0, new BankingTransaction("1", date1, "Amazon", 50F, "Name2", 1000F));

        transactionStore.categorizeTransaction(bankingTransaction, "Name2");

        assertEquals(expected, transactionStore.getBankingTransactions(whichMonth));
    }

    @Test
    void updateCreditTransaction() {
        ArrayList<CreditTransaction> expected = TestDatabase.getTestCreditTransactions();
        CreditTransaction creditTransaction = expected.get(0);
        Calendar date1 = new GregorianCalendar(2020, Calendar.OCTOBER, 25);
        expected.set(0, new CreditTransaction("3589048", date1, "Shell", 20F, "Name2"));

        transactionStore.categorizeTransaction(creditTransaction, "Name2");

        assertEquals(expected, transactionStore.getCreditTransactions(whichMonth));
    }

    @Test
    void getSummaries() {
        ArrayList<TransactionSummary> expected = new ArrayList<>();
        expected.add(new TransactionSummary("Name1", Float.NaN, 70));
        expected.add(new TransactionSummary("Name2", 65, 40));
        expected.add(new TransactionSummary("Name3", 70, Float.NaN));
        expected.add(new TransactionSummary("Test1::sub2", 75, Float.NaN));

        assertEquals(expected, transactionStore.getTransactionSummaries(whichMonth));
    }
}