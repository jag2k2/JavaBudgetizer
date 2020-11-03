package flb.components.editors.tables;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import flb.database.TestDatabase;
import flb.components.categoryselector.*;
import flb.tuples.*;
import java.util.*;

class BankingTableImplTest {
    private BankingTable bankingTable;
    private BankingTableTester tableAutomator;
    private ArrayList<BankingTransaction> expected;

    @BeforeEach
    void setUp() {
        BankingTableImpl bankingTable = new BankingTableImpl(new MenuDisplayerMock());
        this.bankingTable = bankingTable;
        this.tableAutomator = bankingTable;

        expected = TestDatabase.getTestBankingTransactions();
        bankingTable.display(expected);
    }

    @Test
    void display() {
        bankingTable.display(expected);
        assertEquals(expected, tableAutomator.getTransactions());

        expected = new ArrayList<>();
        bankingTable.display(expected);
        assertEquals(expected, tableAutomator.getTransactions());
    }
}