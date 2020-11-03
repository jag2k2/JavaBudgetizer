package flb.components.editors;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import flb.database.*;
import flb.components.editors.tables.*;
import flb.tuples.*;
import flb.util.*;
import java.util.*;

class BankingEditorImplTest {
    private TestDatabase database;
    private BankingEditorImpl bankingEditor;
    private BankingTableTester tableAutomator;
    private ArrayList<BankingTransaction> expected;

    @BeforeEach
    void setUp() {
        this.database = new TestDatabase();
        database.connect();
        TransactionStore transactionStore = new TransactionStoreImp(database);
        this.bankingEditor = new BankingEditorImpl(transactionStore, new CategoryStoreImpl(database));
        this.tableAutomator = bankingEditor.getTableTester();

        expected = TestDatabase.getTestBankingTransactions();
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void refresh() {
        bankingEditor.update(new WhichMonth(2020, Calendar.OCTOBER));
        assertEquals(expected, tableAutomator.getTransactions());

        bankingEditor.update(new WhichMonth(2020, Calendar.JANUARY));
        expected.clear();
        assertEquals(expected, tableAutomator.getTransactions());
    }
}