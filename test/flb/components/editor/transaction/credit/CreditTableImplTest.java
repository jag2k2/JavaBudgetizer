package flb.components.editor.transaction.credit;

import static org.junit.jupiter.api.Assertions.*;
import flb.components.menus.mock.MenuDisplayerMock;
import flb.components.editor.summary.SummarySelectorMock;
import org.junit.jupiter.api.*;
import flb.databases.TestDatabase;
import flb.tuples.*;
import java.util.*;

class CreditTableImplTest {
    private CreditTable creditTable;
    private CreditTableTester tableTester;
    private StatusDisplayer statusDisplayer;
    private ArrayList<Transaction> expected;

    @BeforeEach
    void setUp() {
        CreditTableImpl creditTableImpl = new CreditTableImpl(new MenuDisplayerMock(), new MenuDisplayerMock(), new SummarySelectorMock());
        this.creditTable = creditTableImpl;
        this.tableTester = creditTableImpl;
        this.statusDisplayer = creditTableImpl;

        expected = TestDatabase.getTestCreditTransactions();
    }

    @Test
    void displayAndClearSelection() {
        creditTable.display(expected);
        assertEquals(expected, tableTester.getTransactions());

        expected = new ArrayList<>();
        creditTable.display(expected);
        assertEquals(expected, tableTester.getTransactions());
    }

    @Test
    void displayAndKeepSelection() {
        creditTable.display(expected);
        int[] selectedRows = {0,2};
        tableTester.setSelectedRows(selectedRows);

        creditTable.displayAndKeepSelection(TestDatabase.getTestCreditTransactions());

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