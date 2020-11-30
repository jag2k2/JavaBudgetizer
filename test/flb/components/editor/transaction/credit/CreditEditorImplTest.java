package flb.components.editor.transaction.credit;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import flb.components.editor.summary.SummarySelectorMock;
import flb.databases.*;
import flb.util.Transactions;
import flb.datastores.*;
import flb.components.monthselector.*;
import flb.tuples.*;
import java.util.GregorianCalendar;
import java.util.Calendar;

class CreditEditorImplTest {
    private TestDatabase database;
    private CreditEditorImpl creditEditor;
    private CreditTableTester tableTester;
    private Transactions<CreditTransaction> expected;
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

        expected = CreditListFactory.makeDefaultTransactions();
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
        String[] selectedRefs = CreditFactory.getDefaultRefs(selectedRows);
        float sum = CreditFactory.getSelectedSum(selectedRows);
        String payGroup = GroupNameFactory.createGroupName(date, sum);
        tableTester.setSelectedRows(selectedRows);

        creditEditor.groupSelectedTransactions(date);

        Transactions<CreditTransaction> expected = CreditListFactory.makeGroupedTransactions(selectedRefs, payGroup);
        assertEquals(expected, tableTester.getTransactions());
    }
}