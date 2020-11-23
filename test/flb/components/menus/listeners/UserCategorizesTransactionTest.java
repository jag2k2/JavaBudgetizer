package flb.components.menus.listeners;

import static org.junit.jupiter.api.Assertions.*;

import flb.components.editor.transaction.banking.BankingEditorImpl;
import flb.components.editor.transaction.credit.CreditEditorImpl;
import flb.components.editor.summary.SummarySelectorMock;
import flb.databases.TestDatabase;
import flb.listeners.UserCategorizesTransaction;
import flb.datastores.*;
import flb.components.monthselector.*;
import flb.tuples.*;
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
        database = new TestDatabase();
        database.connect();
        TransactionStore transactionStore = new TransactionStoreImp(database);
        CategoryStore categoryStore = new CategoryStoreImpl(database);
        ViewSelectorImpl monthSelectorImpl = new ViewSelectorImpl();
        monthSelectorImpl.setYear(2020);
        monthSelectorImpl.setMonth(Calendar.OCTOBER);
        bankingEditor = new BankingEditorImpl(transactionStore, categoryStore, monthSelectorImpl, new SummarySelectorMock());
        creditEditor = new CreditEditorImpl(transactionStore, categoryStore, monthSelectorImpl, new SummarySelectorMock());
        transactionStore.addStoreChangeObserver(bankingEditor);
        transactionStore.addStoreChangeObserver(creditEditor);

        testButton = new JButton();
        testButton.setActionCommand("0,Name2");
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void categorizeBankingRow() {
        testButton.addActionListener(new UserCategorizesTransaction(bankingEditor));
        bankingEditor.update();

        testButton.doClick();

        Calendar date = new GregorianCalendar(2020, Calendar.OCTOBER, 25);
        BankingTransaction expected = new BankingTransaction("123", date, "Amazon", -50, "Name2", 1000);
        Transaction actual = bankingEditor.getTableTester().getTransactions().get(0);

        assertEquals(expected, actual);
    }

    @Test
    void categorizeCreditRow() {
        testButton.addActionListener(new UserCategorizesTransaction(creditEditor));
        creditEditor.update();

        testButton.doClick();

        Calendar date = new GregorianCalendar(2020, Calendar.OCTOBER, 25);
        CreditTransaction expected = new CreditTransaction("3589048", date, "Shell", -20, "Name2");
        Transaction actual = creditEditor.getTableAutomator().getTransactions().get(0);

        assertEquals(expected, actual);
    }
}