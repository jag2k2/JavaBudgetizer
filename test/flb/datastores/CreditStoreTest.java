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

class CreditStoreTest {
    private BalanceStore balanceStore;
    private CategoryStore categoryStore;
    private BankingStore bankingStore;
    private CreditStore creditStore;
    private TransactionStoreAdder storeAdder;
    private TestDatabase dataBase;
    private WhichMonth whichMonth;
    private Calendar date20201029;

    @BeforeEach
    void setUp(){
        dataBase = new TestDatabase();
        dataBase.connect();
        DataStoreImpl dataStoreImpl = new DataStoreImpl(dataBase);
        balanceStore = dataStoreImpl;
        categoryStore = dataStoreImpl;
        bankingStore = dataStoreImpl;
        creditStore = dataStoreImpl;
        storeAdder = dataStoreImpl;
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

        assertEquals(expected, bankingStore.getBankingTransactions(whichMonth));
    }

    @Test
    void getCreditTransactions() {
        Transactions<CreditTransaction> expected = CreditListFactory.makeDefaultTransactions();

        assertEquals(expected, creditStore.getCreditTransactions(whichMonth));
    }

    @Test
    void categorizeBankingTransaction() {
        int rowToCategorize = 0;
        String newName = "Name2";
        BankingTransaction bankingTransaction = DebitListFactory.makeDefaultTransactions().get(rowToCategorize);

        creditStore.categorizeTransaction(bankingTransaction, newName);

        String reference = bankingTransaction.getReference();
        Transactions<BankingTransaction> expected = DebitListFactory.makeTransactionsWithCategorizedEntry(reference, newName);
        assertEquals(expected, bankingStore.getBankingTransactions(whichMonth));
    }

    @Test
    void categorizeCreditTransaction() {
        int rowToCategorize = 0;
        String newName = "Name2";
        CreditTransaction creditTransaction = CreditListFactory.makeDefaultTransactions().get(rowToCategorize);

        creditStore.categorizeTransaction(creditTransaction, newName);

        String reference = creditTransaction.getReference();
        Transactions<CreditTransaction> expected = CreditListFactory.makeTransactionsWithCategorizedEntry(reference, newName);
        assertEquals(expected, creditStore.getCreditTransactions(whichMonth));
    }

    @Test
    void groupTransactionsTest() {
        int[] selectedRows = {0,2};
        String[] selectedRefs = CreditFactory.getDefaultRefs(selectedRows);
        Transactions<CreditTransaction> selected = CreditListFactory.makeDefaultTransactions(selectedRefs);
        float sum = CreditFactory.getSelectedSum(selectedRows);
        String payGroup = GroupNameFactory.createGroupName(date20201029, sum);

        creditStore.labelGroup(selected, payGroup);

        Transactions<CreditTransaction> expected = CreditListFactory.makeGroupedTransactions(selectedRefs, payGroup);
        assertEquals(expected, creditStore.getCreditTransactions(whichMonth));
    }

    @Test
    void addBankingTransactions() {
        Transactions<BankingTransaction> expected = DebitListFactory.makeDefaultTransactions();
        Transactions<Transaction> transactionsToAppend = new TransactionsImpl<>();

        storeAdder.addTransactions(transactionsToAppend);
        assertEquals(expected, bankingStore.getBankingTransactions(whichMonth));

        BankingTransaction newTransaction1 = DebitFactory.makeNewTransaction(whichMonth);
        transactionsToAppend.add(newTransaction1);
        expected.add(newTransaction1);

        storeAdder.addTransactions(transactionsToAppend);
        assertEquals(expected, bankingStore.getBankingTransactions(whichMonth));

        storeAdder.addTransactions(transactionsToAppend);
        assertEquals(expected, bankingStore.getBankingTransactions(whichMonth));
    }

    @Test
    void addCreditTransactions() {
        Transactions<CreditTransaction> expected = CreditListFactory.makeDefaultTransactions();
        Transactions<CreditTransaction> transactionsToAppend = new TransactionsImpl<>();

        storeAdder.addTransactions(transactionsToAppend);
        assertEquals(expected, creditStore.getCreditTransactions(whichMonth));

        CreditTransaction newTransaction1 = CreditFactory.makeNewTransaction(whichMonth);
        transactionsToAppend.add(newTransaction1);
        expected.add(newTransaction1);

        storeAdder.addTransactions(transactionsToAppend);
        assertEquals(expected, creditStore.getCreditTransactions(whichMonth));

        storeAdder.addTransactions(transactionsToAppend);
        assertEquals(expected, creditStore.getCreditTransactions(whichMonth));
    }
    @Test
    void getBalance() {
        assertEquals(930F, balanceStore.getBalance(new WhichMonth(2020, Calendar.OCTOBER)));
    }
}