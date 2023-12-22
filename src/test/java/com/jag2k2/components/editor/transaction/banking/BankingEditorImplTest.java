package com.jag2k2.components.editor.transaction.banking;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import com.jag2k2.databases.DebitListFactory;
import com.jag2k2.databases.TestDatabase;
import com.jag2k2.util.Transactions;
import com.jag2k2.components.editor.summary.SummarySelectorMock;
import com.jag2k2.components.editor.transaction.TransactionTableTester;
import com.jag2k2.components.monthselector.MonthSelectorImpl;
import com.jag2k2.components.monthselector.MonthSelector;
import com.jag2k2.datastores.*;
import com.jag2k2.tuples.*;
import java.util.Calendar;

class BankingEditorImplTest {
    private TestDatabase database;
    private BankingEditorImpl bankingEditor;
    private TransactionTableTester tableAutomator;
    private Transactions<BankingTransaction> expected;
    private MonthSelector monthSetter;

    @BeforeEach
    void setUp() {
        database = new TestDatabase();
        database.connect();
        BankingStore bankingStore = new DataStoreImpl(database);
        MonthSelectorImpl monthSelectorImpl = new MonthSelectorImpl();
        monthSetter = monthSelectorImpl;
        monthSetter.setYear(2020);
        monthSetter.setMonth(Calendar.OCTOBER);
        bankingEditor = new BankingEditorImpl(bankingStore, monthSelectorImpl, new SummarySelectorMock());
        tableAutomator = bankingEditor.getTableTester();

        expected = DebitListFactory.makeDefaultTransactions();
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void refresh() {
        monthSetter.setYear(2020);
        monthSetter.setMonth(Calendar.OCTOBER);
        bankingEditor.update();
        assertEquals(expected, tableAutomator.getTransactions());

        monthSetter.setYear(2020);
        monthSetter.setMonth(Calendar.JANUARY);
        bankingEditor.update();
        expected.clear();
        assertEquals(expected, tableAutomator.getTransactions());
    }
}