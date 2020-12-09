package flb.listeners;

import static org.junit.jupiter.api.Assertions.*;
import flb.databases.*;
import flb.datastores.*;
import flb.importers.*;
import flb.importers.file.*;
import flb.importers.ofx.OfxParser;
import flb.tuples.*;
import flb.util.WhichMonth;
import flb.util.Transactions;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.Calendar;

class UserImportsTransactionsListenerTest {
    private JButton testButton;
    private TestDatabase database;
    private TransactionStoreAdder storeAdder;
    private BankingStore bankingStore;
    private CreditStore creditStore;

    @BeforeEach
    void setUp() {
        testButton = new JButton();
        database = new TestDatabase();
        database.connect();
        DataStoreImpl dataStoreImpl = new DataStoreImpl(database);
        storeAdder = dataStoreImpl;
        bankingStore = dataStoreImpl;
        creditStore = dataStoreImpl;
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void importBankingTransactions() {
        Transactions<BankingTransaction> expected = DebitListFactory.makeImportingTransactions();
        expected.addAll(DebitListFactory.makeDefaultTransactions());

        FileLoader fileLoader = new UserChoosesFileLoader(new JFileChooserMock(OfxParser.AccountType.CHECKING), new JFrame());
        TransactionImporter transactionImporter = new QfxImporter(fileLoader);
        testButton.addActionListener(new UserImportsTransactionsListener(storeAdder, transactionImporter));
        testButton.doClick();
        testButton.doClick();

        assertEquals(expected, bankingStore.getBankingTransactions(new WhichMonth(2020, Calendar.OCTOBER)));
    }

    @Test
    void importCreditTransactions() {
        Transactions<CreditTransaction> expected = CreditListFactory.makeImportingTransactions();
        expected.addAll(CreditListFactory.makeDefaultTransactions());

        FileLoader fileLoader = new UserChoosesFileLoader(new JFileChooserMock(OfxParser.AccountType.CREDIT), new JFrame());
        TransactionImporter transactionImporter = new QfxImporter(fileLoader);
        testButton.addActionListener(new UserImportsTransactionsListener(storeAdder, transactionImporter));
        testButton.doClick();
        testButton.doClick();

        assertEquals(expected, creditStore.getCreditTransactions(new WhichMonth(2020, Calendar.OCTOBER)));
    }
}