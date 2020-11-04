package flb.components.editors;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import flb.datastores.*;
import flb.components.editors.tables.*;
import flb.tuples.*;
import flb.util.*;
import java.util.*;

class CreditEditorImplTest {
    private TestDatabase database;
    private CreditEditorImpl creditEditor;
    private CreditTableTester tableAutomator;
    private List<CreditTransaction> expected;


    @BeforeEach
    void setUp() {
        this.database = new TestDatabase();
        database.connect();
        TransactionStore transactionStore = new TransactionStoreImp(database);
        this.creditEditor = new CreditEditorImpl(transactionStore, new CategoryStoreImpl(database));
        this.tableAutomator = creditEditor.getTableAutomator();

        expected = TestDatabase.getTestCreditTransactions();
    }

    @AfterEach
    void tearDown() { database.close(); }

    @Test
    void refresh() {
        creditEditor.update(new WhichMonth(2020, Calendar.OCTOBER));
        assertEquals(expected, tableAutomator.getTransactions());

        creditEditor.update(new WhichMonth(2020, Calendar.JANUARY));
        expected.clear();
        assertEquals(expected, tableAutomator.getTransactions());
    }
}