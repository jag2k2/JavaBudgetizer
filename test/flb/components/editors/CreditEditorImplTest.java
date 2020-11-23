package flb.components.editors;

import static org.junit.jupiter.api.Assertions.*;

import flb.components.editors.mock.SummarySelectorMock;
import flb.databases.TestDatabase;
import org.junit.jupiter.api.*;
import flb.datastores.*;
import flb.components.editors.tables.*;
import flb.components.monthselector.*;
import flb.tuples.*;

import java.util.*;

class CreditEditorImplTest {
    private TestDatabase database;
    private CreditEditorImpl creditEditor;
    private CreditTableTester tableAutomator;
    private List<Transaction> expected;
    private SpecificMonthSetter monthSetter;

    @BeforeEach
    void setUp() {
        database = new TestDatabase();
        database.connect();
        TransactionStore transactionStore = new TransactionStoreImp(database);
        ViewSelectorImpl monthSelectorImpl = new ViewSelectorImpl();
        monthSetter = monthSelectorImpl;

        creditEditor = new CreditEditorImpl(transactionStore, new CategoryStoreImpl(database), monthSelectorImpl, new SummarySelectorMock());
        tableAutomator = creditEditor.getTableAutomator();

        expected = TestDatabase.getTestCreditTransactions();
    }

    @AfterEach
    void tearDown() { database.close(); }

    @Test
    void refresh() {
        monthSetter.setYear(2020);
        monthSetter.setMonth(Calendar.OCTOBER);
        creditEditor.update();
        assertEquals(expected, tableAutomator.getTransactions());

        monthSetter.setYear(2020);
        monthSetter.setMonth(Calendar.JANUARY);
        creditEditor.update();
        expected.clear();
        assertEquals(expected, tableAutomator.getTransactions());
    }
}