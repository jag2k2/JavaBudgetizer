package com.jag2k2.components.menus.listeners;

import static org.junit.jupiter.api.Assertions.*;

import com.jag2k2.components.editor.transaction.banking.BankingEditorImpl;
import com.jag2k2.components.editor.transaction.credit.CreditEditorImpl;
import com.jag2k2.components.editor.summary.SummarySelectorMock;
import com.jag2k2.databases.CreditFactory;
import com.jag2k2.databases.DebitFactory;
import com.jag2k2.databases.TestDatabase;
import com.jag2k2.listeners.UserCategorizesTransaction;
import com.jag2k2.datastores.*;
import com.jag2k2.components.monthselector.*;
import com.jag2k2.tuples.*;
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
        DataStoreImpl dataStoreImpl = new DataStoreImpl(database);
        BankingStore bankingStore = dataStoreImpl;
        CreditStore creditStore = dataStoreImpl;
        MonthSelectorImpl monthSelectorImpl = new MonthSelectorImpl();
        monthSelectorImpl.setYear(2020);
        monthSelectorImpl.setMonth(Calendar.OCTOBER);
        bankingEditor = new BankingEditorImpl(bankingStore, monthSelectorImpl, new SummarySelectorMock());
        creditEditor = new CreditEditorImpl(creditStore, monthSelectorImpl, new SummarySelectorMock());
        creditStore.addStoreChangeObserver(bankingEditor);
        creditStore.addStoreChangeObserver(creditEditor);

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

        BankingTransaction expected = DebitFactory.makeTransactionWithCategory("123", "Name2");
        Transaction actual = bankingEditor.getTableTester().getTransactions().get(0);

        assertEquals(expected, actual);
    }

    @Test
    void categorizeCreditRow() {
        testButton.addActionListener(new UserCategorizesTransaction(creditEditor));
        creditEditor.update();

        testButton.doClick();

        CreditTransaction expected = CreditFactory.makeTransactionWithCategory("3589048", "Name2");
        Transaction actual = creditEditor.getTableAutomator().getTransactions().get(0);

        assertEquals(expected, actual);
    }
}