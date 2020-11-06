package flb.components.categorizer.listeners;

import static org.junit.jupiter.api.Assertions.*;

import flb.components.editors.mock.GoalSelectorMock;
import flb.datastores.*;
import flb.components.editors.BankingEditorImpl;
import flb.components.editors.CreditEditorImpl;
import flb.tuples.*;
import flb.util.WhichMonth;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.*;

class UserCategorizesTransactionTest {
    private JButton testButton;
    private TestDatabase database;
    private BankingEditorImpl bankingEditor;
    private CreditEditorImpl creditEditor;

    @BeforeEach
    void setUp() {
        this.database = new TestDatabase();
        database.connect();
        TransactionStore transactionStore = new TransactionStoreImp(database);
        CategoryStore categoryStore = new CategoryStoreImpl(database);
        this.bankingEditor = new BankingEditorImpl(transactionStore, categoryStore, new GoalSelectorMock());
        this.creditEditor = new CreditEditorImpl(transactionStore, categoryStore, new GoalSelectorMock());

        this.testButton = new JButton();
        testButton.setActionCommand("0,Name2");
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void categorizeBankingRow() {
        testButton.addActionListener(new UserCategorizesTransaction(bankingEditor));
        bankingEditor.update(new WhichMonth(2020,Calendar.OCTOBER));
        bankingEditor.addStoreChangeListener(bankingEditor);

        testButton.doClick();

        Calendar date = new GregorianCalendar(2020, Calendar.OCTOBER, 25);
        BankingTransaction expected = new BankingTransaction("1", date, "Amazon", 50, "Name2", 1000);
        BankingTransaction actual = bankingEditor.getTableTester().getTransactions().get(0);

        assertEquals(expected, actual);
    }

    @Test
    void categorizeCreditRow() {
        testButton.addActionListener(new UserCategorizesTransaction(creditEditor));
        creditEditor.update(new WhichMonth(2020, Calendar.OCTOBER));
        creditEditor.addStoreChangeListener(creditEditor);

        testButton.doClick();

        Calendar date = new GregorianCalendar(2020, Calendar.OCTOBER, 25);
        CreditTransaction expected = new CreditTransaction("3589048", date, "Shell", 20, "Name2");
        CreditTransaction actual = creditEditor.getTableAutomator().getTransactions().get(0);

        assertEquals(expected, actual);
    }
}