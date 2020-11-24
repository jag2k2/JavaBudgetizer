package flb.components.editor.transaction.credit;

import static org.junit.jupiter.api.Assertions.*;

import flb.components.editor.summary.SummarySelectorMock;
import flb.databases.TestDatabase;
import org.junit.jupiter.api.*;
import flb.datastores.*;
import flb.components.monthselector.*;
import flb.tuples.*;

import java.util.*;

class CreditEditorImplTest {
    private TestDatabase database;
    private CreditEditorImpl creditEditor;
    private CreditTableTester tableTester;
    private List<Transaction> expected;
    private MonthSelector monthSetter;

    @BeforeEach
    void setUp() {
        database = new TestDatabase();
        database.connect();
        TransactionStore transactionStore = new TransactionStoreImp(database);
        MonthSelectorImpl monthSelectorImpl = new MonthSelectorImpl();
        monthSetter = monthSelectorImpl;

        creditEditor = new CreditEditorImpl(transactionStore, new CategoryStoreImpl(database), monthSelectorImpl, new SummarySelectorMock());
        transactionStore.addStoreChangeObserver(creditEditor);

        tableTester = creditEditor.getTableAutomator();

        expected = TestDatabase.getTestCreditTransactions();
    }

    @AfterEach
    void tearDown() { database.close(); }

    @Test
    void refresh() {
        monthSetter.setYear(2020);
        monthSetter.setMonth(Calendar.OCTOBER);
        creditEditor.update();
        assertEquals(expected, tableTester.getTransactions());

        monthSetter.setYear(2020);
        monthSetter.setMonth(Calendar.JANUARY);
        creditEditor.update();
        expected.clear();
        assertEquals(expected, tableTester.getTransactions());
    }

    @Test
    void groupSelectedTransactions(){
        Calendar date = new GregorianCalendar(2020, Calendar.OCTOBER, 25);
        monthSetter.setYear(2020);
        monthSetter.setMonth(Calendar.OCTOBER);
        creditEditor.update();

        int[] selectedRows = {0,2};
        tableTester.setSelectedRows(selectedRows);

        creditEditor.groupSelectedTransactions(date);

        List<Transaction> expected = TestDatabase.getTestCreditTransactions();
        assertEquals(expected, tableTester.getTransactions());
    }

    @Test
    void testGroupNameCreator(){
        Calendar date = new GregorianCalendar(2020, Calendar.MAY, 1, 2, 3);
        float sum = -42F;
        String expected = "2020-05-01-0203:-42.00";

        String actual = creditEditor.createGroupName(date, sum);
        assertEquals(expected, actual);

        date = new GregorianCalendar(2020, Calendar.MAY, 1, 14, 3);
        expected = "2020-05-01-1403:-42.00";

        actual = creditEditor.createGroupName(date, sum);
        assertEquals(expected, actual);
    }
}