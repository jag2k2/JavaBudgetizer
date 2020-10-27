package flb.database;

import flb.util.WhichMonth;
import flb.tuples.*;
import org.junit.jupiter.api.*;

import java.lang.reflect.Array;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class TransactionStoreEditorImpTest {
    private TransactionStoreEditorImp transactionStoreEditor;
    private TestDatabase dataBase;
    private Calendar date1;
    private Calendar date2;
    private Calendar date3;

    @BeforeEach
    void setUp(){
        dataBase = new TestDatabase();
        dataBase.connect();
        transactionStoreEditor = new TransactionStoreEditorImp(dataBase);

        date1 = new GregorianCalendar(2020, Calendar.OCTOBER, 25);
        date2 = new GregorianCalendar(2020,Calendar.OCTOBER,26);
        date3 = new GregorianCalendar(2020, Calendar.OCTOBER, 27);
    }

    @AfterEach
    void tearDown() {
        dataBase.close();
    }

    @Test
    void getBankingTransactions() {
        ArrayList<BankingTransaction> expected = new ArrayList<>();
        expected.add(new BankingTransaction("3589045", date1, "Amazon", 50F, "Name1", 1000F));
        expected.add(new BankingTransaction("3589046", date2, "HEB", 40F, "Name2", 960F));
        expected.add(new BankingTransaction("3589047", date3, "Walmart", 30F, "", 930F));

        WhichMonth searchDate = new WhichMonth(2020, Calendar.OCTOBER);

        ArrayList<BankingTransaction> actual = transactionStoreEditor.getBankingTransactions(searchDate);

        assertEquals(expected, actual);
    }

    @Test
    void getCreditTransactions() {
        ArrayList<CreditTransaction> expected = new ArrayList<>();
        expected.add(new CreditTransaction("3589048", date1, "Shell", 20F, "Name1"));
        expected.add(new CreditTransaction("3589049", date2, "Papa Johns", 25F, ""));
        expected.add(new CreditTransaction("3589050", date3, "Torchys", 35F, ""));

        WhichMonth searchDate = new WhichMonth(2020, Calendar.OCTOBER);

        ArrayList<CreditTransaction> actual = transactionStoreEditor.getCreditTransactions(searchDate);

        assertEquals(expected, actual);
    }
}