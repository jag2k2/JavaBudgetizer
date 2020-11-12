package flb.components.editors;

import static org.junit.jupiter.api.Assertions.*;

import flb.components.editors.mock.SummarySelectorMock;
import org.junit.jupiter.api.*;
import flb.datastores.*;
import flb.components.editors.tables.*;
import flb.tuples.*;
import flb.util.*;
import java.util.*;

class BankingEditorImplTest {
    private TestDatabase database;
    private BankingEditorImpl bankingEditor;
    private BankingTableTester tableAutomator;
    private List<BankingTransaction> expected;

    @BeforeEach
    void setUp() {
        this.database = new TestDatabase();
        database.connect();
        TransactionStore transactionStore = new TransactionStoreImp(database);
        this.bankingEditor = new BankingEditorImpl(transactionStore, new CategoryStoreImpl(database), new SummarySelectorMock());
        this.tableAutomator = bankingEditor.getTableTester();

        expected = TestDatabase.getTestBankingTransactions();
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void refresh() {
        WhichMonth dateWithTransactions = new WhichMonth(2020, Calendar.OCTOBER);

        bankingEditor.update(dateWithTransactions);
        assertEquals(expected, tableAutomator.getTransactions());

        WhichMonth dateWithNoTransactions = new WhichMonth(2020, Calendar.JANUARY);
        bankingEditor.update(dateWithNoTransactions);
        expected.clear();
        assertEquals(expected, tableAutomator.getTransactions());
    }
}