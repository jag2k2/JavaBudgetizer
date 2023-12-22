package com.jag2k2.components.editor.transaction.banking;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import com.jag2k2.databases.DebitListFactory;
import com.jag2k2.util.Transactions;
import com.jag2k2.components.editor.summary.SummarySelectorMock;
import com.jag2k2.components.editor.transaction.*;
import com.jag2k2.components.menus.mock.*;
import com.jag2k2.util.TransactionsImpl;
import com.jag2k2.tuples.*;


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