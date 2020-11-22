package flb.listeners;

import flb.databases.TestDatabase;
import flb.datastores.*;
import flb.importers.*;
import flb.importers.file.JFileChooserMock;
import flb.importers.ofx.OfxParser;
import org.junit.jupiter.api.*;
import javax.swing.*;


class UserImportsTransactionsListenerTest {
    private JButton testButton;
    private TestDatabase database;

    @BeforeEach
    void setUp() {
        testButton = new JButton();
        database = new TestDatabase();
        database.connect();
        TransactionStore transactionStore = new TransactionStoreImp(database);
        TransactionImporter transactionImporter = new QfxImporter(new JFrame(), new JFileChooserMock(OfxParser.AccountType.CHECKING));

        testButton.addActionListener(new UserImportsTransactionsListener(transactionStore, transactionImporter));
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void actionPerformed() {
        testButton.doClick();
        testButton.doClick();
    }
}