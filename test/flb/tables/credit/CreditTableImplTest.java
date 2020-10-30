package flb.tables.credit;

import static org.junit.jupiter.api.Assertions.*;
import flb.database.TestDatabase;
import flb.tables.credit.interfaces.*;
import flb.tuples.*;
import java.util.*;
import org.junit.jupiter.api.*;

class CreditTableImplTest {
    private CreditTable creditTable;
    private CreditTableAutomator tableAutomator;
    private ArrayList<CreditTransaction> expected;

    @BeforeEach
    void setUp() {
        CreditTableImpl creditTableImpl = new CreditTableImpl();
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