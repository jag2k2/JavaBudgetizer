package flb.tables.banking;

import static org.junit.jupiter.api.Assertions.*;
import flb.database.*;
import flb.tables.banking.interfaces.BankingTableAutomator;
import flb.tuples.BankingTransaction;
import flb.util.WhichMonth;
import org.junit.jupiter.api.*;
import java.util.*;

class BankingEditorImplTest {
    private TestDatabase database;
    private BankingEditorImpl bankingEditor;
    private BankingTableAutomator tableAutomator;
    private ArrayList<BankingTransaction> expected;

    @BeforeEach
    void setUp() {
        this.database = new TestDatabase();
        database.connect();
        TransactionStoreImp transactionStore = new TransactionStoreImp(database);
        this.bankingEditor = new BankingEditorImpl(transactionStore);
        this.tableAutomator = bankingEditor.getTableAutomator();

        expected = TestDatabase.getTestBankingTransactions();
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void refresh() {
        bankingEditor.refresh(new WhichMonth(2020, Calendar.OCTOBER));
        assertEquals(expected, tableAutomator.getTransactions());

        bankingEditor.refresh(new WhichMonth(2020, Calendar.JANUARY));
        expected.clear();
        assertEquals(expected, tableAutomator.getTransactions());
    }
}