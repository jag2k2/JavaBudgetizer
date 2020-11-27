package flb.datastores;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import flb.components.editor.transaction.credit.GroupNameFactory;
import flb.databases.*;
import flb.util.TransactionsImpl;
import flb.util.WhichMonth;
import flb.util.Transactions;
import flb.tuples.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

class TransactionStoreImpTest {
    private TransactionStore transactionStore;
    private TestDatabase dataBase;
    private WhichMonth whichMonth;
    private Calendar date20201029;

    @BeforeEach
    void setUp(){
        dataBase = new TestDatabase();
        dataBase.connect();
        transactionStore = new TransactionStoreImp(dataBase);
        whichMonth = new WhichMonth(2020, Calendar.OCTOBER);
        date20201029 = new GregorianCalendar(2020, Calendar.OCTOBER, 29);
    }

    @AfterEach
    void tearDown() {
        dataBase.close();
    }

    @Test
    void getBankingTransactions() {
        Transactions<BankingTransaction> expected = DebitFactory.makeTransactions();

        assertEquals(expected, transactionStore.getBankingTransactions(whichMonth));
    }

    @Test
    void getCreditTransactions() {
        Transactions<CreditTransaction> expected = CreditFactory.makeTransactions();

        assertEquals(expected, transactionStore.getCreditTransactions(whichMonth));
    }

    @Test
    void categorizeBankingTransaction() {
        int rowToCategorize = 0;
        String newName = "Name2";
        BankingTransaction bankingTransaction = DebitFactory.makeTransactions().get(rowToCategorize);

        transactionStore.categorizeTransaction(bankingTransaction, newName);

        Transactions<BankingTransaction> expected = DebitFactory.makeTransactionsWithCategorizedEntry(rowToCategorize, newName);
        assertEquals(expected, transactionStore.getBankingTransactions(whichMonth));
    }

    @Test
    void categorizeCreditTransaction() {
        int rowToCategorize = 0;
        String newName = "Name2";
        CreditTransaction creditTransaction = CreditFactory.makeTransactions().get(rowToCategorize);

        transactionStore.categorizeTransaction(creditTransaction, newName);

        Transactions<CreditTransaction> expected = CreditFactory.makeTransactionsWithCategorizedEntry(rowToCategorize, newName);
        assertEquals(expected, transactionStore.getCreditTransactions(whichMonth));
    }

    @Test
    void groupTransactionsTest() {
        int[] selectedRows = {0,2};
        Transactions<CreditTransaction> selected = CreditFactory.makeTransactions(selectedRows);

        float sum = CreditFactory.getTransactionSum(selectedRows);
        transactionStore.labelGroup(selected, GroupNameFactory.createGroupName(date20201029, sum));

        Transactions<CreditTransaction> expected = CreditFactory.makeGroupedTransactions(selectedRows, date20201029);
        assertEquals(expected, transactionStore.getCreditTransactions(whichMonth));
    }

    @Test
    void addBankingTransactions() {
        Transactions<BankingTransaction> expected = DebitFactory.makeTransactions();
        Transactions<Transaction> transactionsToAppend = new TransactionsImpl<>();

        transactionStore.addTransactions(transactionsToAppend);
        assertEquals(expected, transactionStore.getBankingTransactions(whichMonth));

        BankingTransaction newTransaction1 = new BankingTransaction("7",
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
        Transactions<CreditTransaction> expected = CreditFactory.makeTransactions();
        Transactions<CreditTransaction> transactionsToAppend = new TransactionsImpl<>();

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
}