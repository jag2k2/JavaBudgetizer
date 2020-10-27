package flb.tables;

import flb.database.*;
import flb.tuples.BankingTransaction;
import flb.util.WhichMonth;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class BankingTableEditorImpTest {
    private BankingTableEditorImp bankingEditor;
    private BankingTableModelImp tableModel;
    private TestDatabase database;
    private ArrayList<BankingTransaction> expected;

    @BeforeEach
    void setUp() {
        this.database = new TestDatabase();
        database.connect();
        TransactionStoreEditorImp storeEditor = new TransactionStoreEditorImp(database);
        this.tableModel = new BankingTableModelImp();
        JTable table = new JTable(tableModel);
        BankingTableImp bankingTable = new BankingTableImp(table, tableModel);
        this.bankingEditor = new BankingTableEditorImp(storeEditor, bankingTable);

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
        bankingEditor.refreshAndClearSelection(new WhichMonth(2020, Calendar.OCTOBER));
        assertEquals(expected, tableModel.getContents());

        bankingEditor.refreshAndClearSelection(new WhichMonth(2020, Calendar.JANUARY));
        expected.clear();
        assertEquals(expected, tableModel.getContents());
    }
}