package flb.components.editor.transaction.banking;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import flb.databases.DebitListFactory;
import flb.util.Transactions;
import flb.components.editor.summary.SummarySelectorMock;
import flb.components.editor.transaction.*;
import flb.components.menus.mock.*;
import flb.util.TransactionsImpl;
import flb.tuples.*;


class BankingTableImplTest {
    private TransactionTable<BankingTransaction> bankingTable;
    private TransactionTableTester tableAutomator;
    private Transactions<BankingTransaction> expected;

    @BeforeEach
    void setUp() {
        BankingTableImpl bankingTable = new BankingTableImpl(new MenuDisplayerMock(), new SummarySelectorMock());
        this.bankingTable = bankingTable;
        this.tableAutomator = bankingTable;

        expected = DebitListFactory.makeDefaultTransactions();
        bankingTable.display(expected);
    }

    @Test
    void display() {
        bankingTable.display(expected);
        assertEquals(expected, tableAutomator.getTransactions());

        expected = new TransactionsImpl<>();
        bankingTable.display(expected);
        assertEquals(expected, tableAutomator.getTransactions());
    }
}