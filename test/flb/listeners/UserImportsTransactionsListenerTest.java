package flb.listeners;

import static org.junit.jupiter.api.Assertions.*;
import flb.databases.TestDatabase;
import flb.datastores.*;
import flb.importers.*;
import flb.importers.file.*;
import flb.importers.ofx.OfxParser;
import flb.tuples.*;
import flb.util.WhichMonth;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.*;

class UserImportsTransactionsListenerTest {
    private JButton testButton;
    private TestDatabase database;
    private List<Transaction> expected;
    private TransactionStore transactionStore;

    @BeforeEach
    void setUp() {
        testButton = new JButton();
        database = new TestDatabase();
        database.connect();
        transactionStore = new TransactionStoreImp(database);
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void importBankingTransactinos() {
        expected = ImportingTransactions.getDebitTransactions();
        expected.addAll(TestDatabase.getTestBankingTransactions());

        FileLoader fileLoader = new UserChoosesFileLoader(new JFileChooserMock(OfxParser.AccountType.CHECKING), new JFrame());
        TransactionImporter transactionImporter = new QfxImporter(fileLoader);
        testButton.addActionListener(new UserImportsTransactionsListener(transactionStore, transactionImporter));
        testButton.doClick();
        testButton.doClick();

        assertEquals(expected, transactionStore.getBankingTransactions(new WhichMonth(2020, Calendar.OCTOBER)));
    }

    @Test
    void importCreditTransactions() {
        expected = ImportingTransactions.getCreditTransactions();
        expected.addAll(TestDatabase.getTestCreditTransactions());

        FileLoader fileLoader = new UserChoosesFileLoader(new JFileChooserMock(OfxParser.AccountType.CREDIT), new JFrame());
        TransactionImporter transactionImporter = new QfxImporter(fileLoader);
        testButton.addActionListener(new UserImportsTransactionsListener(transactionStore, transactionImporter));
        testButton.doClick();
        testButton.doClick();

        assertEquals(expected, transactionStore.getCreditTransactions(new WhichMonth(2020, Calendar.OCTOBER)));
    }
}