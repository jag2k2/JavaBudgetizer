package com.jag2k2.components.editor.transaction.credit;

import static org.junit.jupiter.api.Assertions.*;
import com.jag2k2.components.menus.mock.MenuDisplayerMock;
import com.jag2k2.components.editor.summary.SummarySelectorMock;
import com.jag2k2.databases.*;
import com.jag2k2.util.TransactionsImpl;
import org.junit.jupiter.api.*;
import com.jag2k2.tuples.*;
import com.jag2k2.util.Transactions;
import java.util.Arrays;

class CreditTableImplTest {
    private CreditTable creditTable;
    private CreditTableTester tableTester;
    private StatusDisplayer statusDisplayer;
    private Transactions<CreditTransaction> expected;

    @BeforeEach
    void setUp() {
        CreditTableImpl creditTableImpl = new CreditTableImpl(new MenuDisplayerMock(), new MenuDisplayerMock(), new SummarySelectorMock());
        this.creditTable = creditTableImpl;
        this.tableTester = creditTableImpl;
        this.statusDisplayer = creditTableImpl;

        expected = CreditListFactory.makeDefaultTransactions();
    }

    @Test
    void displayAndClearSelection() {
        creditTable.display(expected);
        assertEquals(expected, tableTester.getTransactions());

        expected = new TransactionsImpl<>();
        creditTable.display(expected);
        assertEquals(expected, tableTester.getTransactions());
    }

    @Test
    void displayAndKeepSelection() {
        creditTable.display(expected);
        int[] selectedRows = {0,2};
        tableTester.setSelectedRows(selectedRows);

        creditTable.displayAndKeepSelection(CreditListFactory.makeDefaultTransactions());

        assertTrue(Arrays.equals(selectedRows, tableTester.getSelectedRows()));
    }

    @Test
    void statusBarTest() {
        creditTable.display(expected);
        int[] selectedRows = {0,2};
        tableTester.setSelectedRows(selectedRows);

        statusDisplayer.displaySelectionStatus();

        assertEquals("Count: 2     Sum: -55.00", tableTester.getStatusText());
    }
}