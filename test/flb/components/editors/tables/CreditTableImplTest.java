package flb.components.editors.tables;

import static org.junit.jupiter.api.Assertions.*;

import flb.components.menus.mock.MenuDisplayerMock;
import flb.components.editors.mock.SummarySelectorMock;
import org.junit.jupiter.api.*;
import flb.datastores.TestDatabase;
import flb.tuples.*;
import java.util.*;

class CreditTableImplTest {
    private CreditTable creditTable;
    private CreditTableTester tableAutomator;
    private ArrayList<CreditTransaction> expected;

    @BeforeEach
    void setUp() {
        CreditTableImpl creditTableImpl = new CreditTableImpl(new MenuDisplayerMock(), new SummarySelectorMock());
        this.creditTable = creditTableImpl;
        this.tableAutomator = creditTableImpl;

        expected = TestDatabase.getTestCreditTransactions();
    }

    @Test
    void displayAndClearSelection() {
        creditTable.displayAndClearSelection(expected);
        assertEquals(expected, tableAutomator.getTransactions());

        expected = new ArrayList<>();
        creditTable.displayAndClearSelection(expected);
        assertEquals(expected, tableAutomator.getTransactions());
    }
}