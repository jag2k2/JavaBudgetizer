package flb.components.editor.transaction.banking;

import static org.junit.jupiter.api.Assertions.*;

import flb.components.editor.summary.SummarySelectorMock;
import flb.components.editor.transaction.TransactionTableTester;
import flb.components.monthselector.MonthSelectorImpl;
import flb.components.monthselector.MonthSelector;
import flb.databases.TestDatabase;
import org.junit.jupiter.api.*;
import flb.datastores.*;
import flb.tuples.*;

import java.util.*;

class BankingEditorImplTest {
    private TestDatabase database;
    private BankingEditorImpl bankingEditor;
    private TransactionTableTester tableAutomator;
    private List<Transaction> expected;
    private MonthSelector monthSetter;

    @BeforeEach
    void setUp() {
        database = new TestDatabase();
        database.connect();
        TransactionStore transactionStore = new TransactionStoreImp(database);
        MonthSelectorImpl monthSelectorImpl = new MonthSelectorImpl();
        monthSetter = monthSelectorImpl;
        monthSetter.setYear(2020);
        monthSetter.setMonth(Calendar.OCTOBER);
        bankingEditor = new BankingEditorImpl(transactionStore, new CategoryStoreImpl(database), monthSelectorImpl, new SummarySelectorMock());
        tableAutomator = bankingEditor.getTableTester();

        expected = TestDatabase.getTestBankingTransactions();
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