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

        Calendar date1 = new GregorianCalendar(2020, Calendar.OCTOBER, 25);
        Calendar date2 = new GregorianCalendar(2020,Calendar.OCTOBER,26);
        Calendar date3 = new GregorianCalendar(2020, Calendar.OCTOBER, 27);

        expected = new ArrayList<>();
        expected.add(new BankingTransaction("3589045", date1, "Amazon", 50F, "Name1", 1000F));
        expected.add(new BankingTransaction("3589046", date2, "HEB", 40F, "Name2", 960F));
        expected.add(new BankingTransaction("3589047", date3, "Walmart", 30F, "", 930F));
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void refreshAndClearSelection() {
        bankingEditor.refresh(new WhichMonth(2020, Calendar.OCTOBER));
        assertEquals(expected, tableAutomator.getTransactions());

        bankingEditor.refresh(new WhichMonth(2020, Calendar.JANUARY));
        expected.clear();
        assertEquals(expected, tableAutomator.getTransactions());
    }
}