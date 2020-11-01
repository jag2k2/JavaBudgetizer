package flb.application.main.listeners;

import static org.junit.jupiter.api.Assertions.*;
import flb.database.*;
import flb.database.interfaces.TransactionStore;
import flb.tables.banking.*;
import flb.tuples.*;
import flb.util.WhichMonth;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.*;

class UserCategorizesBankingTransactionTest {
    private JButton testButton;
    private TestDatabase database;
    private TransactionStore transactionStore;
    private BankingEditorImpl bankingEditor;

    @BeforeEach
    void setUp() {
        this.database = new TestDatabase();
        database.connect();
        this.transactionStore = new TransactionStoreImp(database);
        this.bankingEditor = new BankingEditorImpl(transactionStore);
        bankingEditor.refresh(new WhichMonth(2020,Calendar.OCTOBER));

        this.testButton = new JButton();
        testButton.addActionListener(new UserCategorizesBankingTransaction(bankingEditor));

    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void categorizeRow() {
        testButton.setActionCommand("0,Name2");

        testButton.doClick();

        Calendar date = new GregorianCalendar(2020, Calendar.OCTOBER, 25);
        BankingTransaction expected = new BankingTransaction("1", date, "Amazon", 50, "Name2", 1000);
        BankingTransaction actual = bankingEditor.getTableAutomator().getTransactions().get(0);

        assertEquals(expected, actual);
    }
}