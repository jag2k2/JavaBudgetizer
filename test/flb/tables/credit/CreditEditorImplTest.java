package flb.tables.credit;

import static org.junit.jupiter.api.Assertions.*;

import flb.util.WhichMonth;
import org.junit.jupiter.api.*;
import flb.database.*;
import flb.tables.credit.interfaces.*;
import flb.tuples.*;

import java.util.ArrayList;
import java.util.Calendar;

class CreditEditorImplTest {
    private TestDatabase database;
    private CreditEditorImpl creditEditor;
    private CreditTableAutomator tableAutomator;
    private ArrayList<CreditTransaction> expected;


    @BeforeEach
    void setUp() {
        this.database = new TestDatabase();
        database.connect();
        TransactionStoreImp transactionStore = new TransactionStoreImp(database);
        this.creditEditor = new CreditEditorImpl(transactionStore);
        this.tableAutomator = creditEditor.getTableAutomator();

        expected = TestDatabase.getTestCreditTransactions();
    }

    @AfterEach
    void tearDown() { database.close(); }

    @Test
    void refresh() {
        creditEditor.refresh(new WhichMonth(2020, Calendar.OCTOBER));
        assertEquals(expected, tableAutomator.getTransactions());

        creditEditor.refresh(new WhichMonth(2020, Calendar.JANUARY));
        expected.clear();
        assertEquals(expected, tableAutomator.getTransactions());
    }
}