package flb.application.main.listeners;

import static org.junit.jupiter.api.Assertions.*;

import flb.database.*;
import flb.database.interfaces.TransactionStore;
import flb.tables.credit.*;
import flb.tuples.*;
import flb.util.WhichMonth;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.*;

class UserCategorizesCreditTransactionTest {
    private JButton testButton;
    private TestDatabase database;
    private CreditEditorImpl creditEditor;

    @BeforeEach
    void setUp() {
        this.database = new TestDatabase();
        database.connect();
        TransactionStore transactionStore = new TransactionStoreImp(database);
        this.creditEditor = new CreditEditorImpl(transactionStore);
        creditEditor.update(new WhichMonth(2020,Calendar.OCTOBER));
        creditEditor.addStoreChangeListener(creditEditor);

        this.testButton = new JButton();

        testButton.addActionListener(new UserCategorizesTransaction(creditEditor));
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
        CreditTransaction expected = new CreditTransaction("3589048", date, "Shell", 20, "Name2");
        CreditTransaction actual = creditEditor.getTableAutomator().getTransactions().get(0);

        assertEquals(expected, actual);
    }
}