package flb.datastores;

import static org.junit.jupiter.api.Assertions.*;

import flb.databases.TestDatabase;
import flb.util.WhichMonth;
import flb.tuples.*;
import org.junit.jupiter.api.*;
import java.util.*;

class TransactionStoreImpTest {
    private TransactionStore transactionStore;
    private TestDatabase dataBase;
    private WhichMonth whichMonth;

    @BeforeEach
    void setUp(){
        dataBase = new TestDatabase();
        dataBase.connect();
        transactionStore = new TransactionStoreImp(dataBase);
        whichMonth = new WhichMonth(2020, Calendar.OCTOBER);
    }

    @AfterEach
    void tearDown() {
        dataBase.close();
    }

    @Test
    void getBankingTransactions() {
        ArrayList<Transaction> expected = TestDatabase.getTestBankingTransactions();

        assertEquals(expected, transactionStore.getBankingTransactions(whichMonth));
    }

    @Test
    void getCreditTransactions() {
        ArrayList<Transaction> expected = TestDatabase.getTestCreditTransactions();

        assertEquals(expected, transactionStore.getCreditTransactions(whichMonth));
    }

    @Test
    void categorizeBankingTransaction() {
        ArrayList<Transaction> expected = TestDatabase.getTestBankingTransactions();
        Transaction bankingTransaction = expected.get(0);
        Calendar date1 = new GregorianCalendar(2020, Calendar.OCTOBER, 25);
        expected.set(0, new BankingTransaction("123", date1, "Amazon", -50F, "Name2", 1000F));

        transactionStore.categorizeTransaction(bankingTransaction, "Name2");

        assertEquals(expected, transactionStore.getBankingTransactions(whichMonth));
    }

    @Test
    void categorizeCreditTransaction() {
        ArrayList<Transaction> expected = TestDatabase.getTestCreditTransactions();
        Transaction creditTransaction = expected.get(0);
        Calendar date1 = new GregorianCalendar(2020, Calendar.OCTOBER, 25);
        expected.set(0, new CreditTransaction("3589048", date1, "Shell", -20F, "Name2", ""));

        transactionStore.categorizeTransaction(creditTransaction, "Name2");

        assertEquals(expected, transactionStore.getCreditTransactions(whichMonth));
    }

    @Test
    void groupTransactionsTest() {
        List<Transaction> selected = TestDatabase.getTestCreditTransactions();
        selected.remove(1);

        transactionStore.labelGroup(selected, "TestGroupName");

        List<Transaction> expected = TestDatabase.getTestCreditTransactions();
        assertEquals(expected, transactionStore.getCreditTransactions(whichMonth));
    }

    @Test
    void addBankingTransactions() {
        ArrayList<Transaction> expected = TestDatabase.getTestBankingTransactions();
        ArrayList<Transaction> transactionsToAppend = new ArrayList<>();

        transactionStore.addTransactions(transactionsToAppend);
        assertEquals(expected, transactionStore.getBankingTransactions(whichMonth));

        Transaction newTransaction1 = new BankingTransaction("7",
                new GregorianCalendar(2020, Calendar.OCTOBER, 28),
                "Taco Bell", 14.53F, "", 100F);
        transactionsToAppend.add(newTransaction1);
        expected.add(newTransaction1);

        transactionStore.addTransactions(transactionsToAppend);
        assertEquals(expected, transactionStore.getBankingTransactions(whichMonth));

        transactionStore.addTransactions(transactionsToAppend);
        assertEquals(expected, transactionStore.getBankingTransactions(whichMonth));
    }

    @Test
    void addCreditTransactions() {
        List<Transaction> expected = TestDatabase.getTestCreditTransactions();
        ArrayList<Transaction> transactionsToAppend = new ArrayList<>();

        transactionStore.addTransactions(transactionsToAppend);
        assertEquals(expected, transactionStore.getCreditTransactions(whichMonth));

        CreditTransaction newTransaction1 = new CreditTransaction("202010281",
                new GregorianCalendar(2020, Calendar.OCTOBER, 28),
                "Torchy's", 14.53F, "", "");
        transactionsToAppend.add(newTransaction1);
        expected.add(newTransaction1);

        transactionStore.addTransactions(transactionsToAppend);
        assertEquals(expected, transactionStore.getCreditTransactions(whichMonth));

        transactionStore.addTransactions(transactionsToAppend);
        assertEquals(expected, transactionStore.getCreditTransactions(whichMonth));
    }

    @Test
    void getSummaries() {
        ArrayList<TransactionSummary> expected = new ArrayList<>();

        System.out.println(transactionStore.getTransactionSummaries(whichMonth));
    }
}