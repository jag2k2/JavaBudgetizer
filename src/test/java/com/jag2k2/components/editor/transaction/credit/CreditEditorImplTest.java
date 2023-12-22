package com.jag2k2.components.editor.transaction.credit;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import com.jag2k2.components.editor.summary.SummarySelectorMock;
import com.jag2k2.databases.*;
import com.jag2k2.util.Transactions;
import com.jag2k2.datastores.*;
import com.jag2k2.components.monthselector.*;
import com.jag2k2.tuples.*;
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
        CreditStore creditStore = new DataStoreImpl(database);
        MonthSelectorImpl monthSelectorImpl = new MonthSelectorImpl();
        monthSetter = monthSelectorImpl;

        creditEditor = new CreditEditorImpl(creditStore, monthSelectorImpl, new SummarySelectorMock());
        creditStore.addStoreChangeObserver(creditEditor);

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