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

class TransactionStoreTest {
    private TransactionStore transactionStore;
    private TestDatabase dataBase;
    private WhichMonth whichMonth;
    private Calendar date20201029;

    @BeforeEach
    void setUp(){
        dataBase = new TestDatabase();
        dataBase.connect();
        transactionStore = new DataStoreImpl(dataBase);
        whichMonth = new WhichMonth(2020, Calendar.OCTOBER);
        date20201029 = new GregorianCalendar(2020, Calendar.OCTOBER, 29);
    }

    @AfterEach
    void tearDown() {
        dataBase.close();
    }

    @Test
    void getBankingTransactions() {
        Transactions<BankingTransaction> expected = DebitListFactory.makeDefaultTransactions();

        assertEquals(expected, transactionStore.getBankingTransactions(whichMonth));
    }

    @Test
    void getCreditTransactions() {
        Transactions<CreditTransaction> expected = CreditListFactory.makeDefaultTransactions();

        assertEquals(expected, transactionStore.getCreditTransactions(whichMonth));
    }

    @Test
    void categorizeBankingTransaction() {
        int rowToCategorize = 0;
        String newName = "Name2";
        BankingTransaction bankingTransaction = DebitListFactory.makeDefaultTransactions().get(rowToCategorize);

        transactionStore.categorizeTransaction(bankingTransaction, newName);

        String reference = bankingTransaction.getReference();
        Transactions<BankingTransaction> expected = DebitListFactory.makeTransactionsWithCategorizedEntry(reference, newName);
        assertEquals(expected, transactionStore.getBankingTransactions(whichMonth));
    }

    @Test
    void categorizeCreditTransaction() {
        int rowToCategorize = 0;
        String newName = "Name2";
        CreditTransaction creditTransaction = CreditListFactory.makeDefaultTransactions().get(rowToCategorize);

        transactionStore.categorizeTransaction(creditTransaction, newName);

        String reference = creditTransaction.getReference();
        Transactions<CreditTransaction> expected = CreditListFactory.makeTransactionsWithCategorizedEntry(reference, newName);
        assertEquals(expected, transactionStore.getCreditTransactions(whichMonth));
    }

    @Test
    void groupTransactionsTest() {
        int[] selectedRows = {0,2};
        String[] selectedRefs = CreditFactory.getDefaultRefs(selectedRows);
        Transactions<CreditTransaction> selected = CreditListFactory.makeDefaultTransactions(selectedRefs);
        float sum = CreditFactory.getSelectedSum(selectedRows);
        String payGroup = GroupNameFactory.createGroupName(date20201029, sum);

        transactionStore.labelGroup(selected, payGroup);

        Transactions<CreditTransaction> expected = CreditListFactory.makeGroupedTransactions(selectedRefs, payGroup);
        assertEquals(expected, transactionStore.getCreditTransactions(whichMonth));
    }

    @Test
    void addBankingTransactions() {
        Transactions<BankingTransaction> expected = DebitListFactory.makeDefaultTransactions();
        Transactions<Transaction> transactionsToAppend = new TransactionsImpl<>();

        transactionStore.addTransactions(transactionsToAppend);
        assertEquals(expected, transactionStore.getBankingTransactions(whichMonth));

        BankingTransaction newTransaction1 = DebitFactory.makeNewTransaction(whichMonth);
        transactionsToAppend.add(newTransaction1);
        expected.add(newTransaction1);

        transactionStore.addTransactions(transactionsToAppend);
        assertEquals(expected, transactionStore.getBankingTransactions(whichMonth));

        transactionStore.addTransactions(transactionsToAppend);
        assertEquals(expected, transactionStore.getBankingTransactions(whichMonth));
    }

    @Test
    void addCreditTransactions() {
        Transactions<CreditTransaction> expected = CreditListFactory.makeDefaultTransactions();
        Transactions<CreditTransaction> transactionsToAppend = new TransactionsImpl<>();

        transactionStore.addTransactions(transactionsToAppend);
        assertEquals(expected, transactionStore.getCreditTransactions(whichMonth));

        CreditTransaction newTransaction1 = CreditFactory.makeNewTransaction(whichMonth);
        transactionsToAppend.add(newTransaction1);
        expected.add(newTransaction1);

        transactionStore.addTransactions(transactionsToAppend);
        assertEquals(expected, transactionStore.getCreditTransactions(whichMonth));

        transactionStore.addTransactions(transactionsToAppend);
        assertEquals(expected, transactionStore.getCreditTransactions(whichMonth));
    }
}