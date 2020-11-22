package flb.components.editors;

import static org.junit.jupiter.api.Assertions.*;

import flb.components.editors.mock.SummarySelectorMock;
import flb.components.monthselector.MonthSelectorImpl;
import flb.components.monthselector.SpecificMonthSetter;
import flb.databases.TestDatabase;
import org.junit.jupiter.api.*;
import flb.datastores.*;
import flb.components.editors.tables.*;
import flb.tuples.*;

import java.util.*;

class BankingEditorImplTest {
    private TestDatabase database;
    private BankingEditorImpl bankingEditor;
    private BankingTableTester tableAutomator;
    private List<Transaction> expected;
    private SpecificMonthSetter monthSetter;

    @BeforeEach
    void setUp() {
        this.database = new TestDatabase();
        database.connect();
        TransactionStore transactionStore = new TransactionStoreImp(database);
        MonthSelectorImpl monthSelectorImpl = new MonthSelectorImpl();
        this.monthSetter = monthSelectorImpl;
        monthSetter.setYear(2020);
        monthSetter.setMonth(Calendar.OCTOBER);
        this.bankingEditor = new BankingEditorImpl(transactionStore, new CategoryStoreImpl(database), monthSelectorImpl, new SummarySelectorMock());
        this.tableAutomator = bankingEditor.getTableTester();

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