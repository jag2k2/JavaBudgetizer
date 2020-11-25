package flb.components.editor.transaction.banking;

import static org.junit.jupiter.api.Assertions.*;

import flb.components.editor.summary.SummarySelectorMock;
import flb.components.editor.transaction.TransactionTable;
import flb.components.editor.transaction.TransactionTableTester;
import flb.components.menus.mock.*;
import org.junit.jupiter.api.*;
import flb.databases.TestDatabase;
import flb.tuples.*;
import java.util.*;

class BankingTableImplTest {
    private BankingTable bankingTable;
    private TransactionTableTester tableAutomator;
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