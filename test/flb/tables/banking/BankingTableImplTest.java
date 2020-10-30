package flb.tables.banking;

import static org.junit.jupiter.api.Assertions.*;

import flb.database.TestDatabase;
import flb.tables.banking.interfaces.*;
import flb.tuples.*;
import org.junit.jupiter.api.*;
import java.util.*;

class BankingTableImplTest {
    private BankingTable bankingTable;
    private BankingTableAutomator tableAutomator;
    private ArrayList<BankingTransaction> expected;

    @BeforeEach
    void setUp() {
        BankingTableImpl bankingTableImpl = new BankingTableImpl();
        this.bankingTable = bankingTableImpl;
        this.tableAutomator = bankingTableImpl;

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