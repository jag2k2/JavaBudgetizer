package flb.components.editors.tables;

import static org.junit.jupiter.api.Assertions.*;

import flb.components.menus.mock.*;
import flb.components.editors.mock.*;
import org.junit.jupiter.api.*;
import flb.datastores.TestDatabase;
import flb.tuples.*;
import java.util.*;

class BankingTableImplTest {
    private BankingTable bankingTable;
    private BankingTableTester tableAutomator;
    private ArrayList<BankingTransaction> expected;

    @BeforeEach
    void setUp() {
        BankingTableImpl bankingTable = new BankingTableImpl(new MenuDisplayerMock(), new SummarySelectorMock());
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