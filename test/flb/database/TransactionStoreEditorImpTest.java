package flb.database;

import flb.util.WhichMonth;
import flb.tuples.*;
import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class TransactionStoreEditorImpTest {
    private TransactionStoreEditorImp transactionStoreEditor;
    private TestDatabase dataBase;
    ArrayList<BankingTransaction> expected;
    ArrayList<BankingTransaction> actual;

    @BeforeEach
    void setUp(){
        expected = new ArrayList<>();
        actual = new ArrayList<>();

        dataBase = new TestDatabase();
        dataBase.connect();
        transactionStoreEditor = new TransactionStoreEditorImp(dataBase);

        Calendar date1 = new GregorianCalendar(2020, Calendar.OCTOBER, 25);
        Calendar date2 = new GregorianCalendar(2020,Calendar.OCTOBER,26);
        Calendar date3 = new GregorianCalendar(2020, Calendar.OCTOBER, 27);

        expected.add(new BankingTransaction("3589045", date1, "Amazon", 50F, "Name1", 1000F));
        expected.add(new BankingTransaction("3589046", date2, "HEB", 40F, "Name2", 960F));
        expected.add(new BankingTransaction("3589047", date3, "Walmart", 30F, "", 930F));
    }

    @AfterEach
    void tearDown() {
        dataBase.close();
    }

    @Test
    void getBankingTransactions() {
        WhichMonth searchDate = new WhichMonth(2020, Calendar.OCTOBER);

        actual = transactionStoreEditor.getBankingTransactions(searchDate);

        assertEquals(expected, actual);
    }
}