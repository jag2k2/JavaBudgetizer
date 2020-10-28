package flb.tables;

import flb.tables.banking.BankingTableModelImp;
import flb.tuples.BankingTransaction;
import java.util.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class BankingTableModelImpTest {
    private BankingTableModelImp tableModel;

    @BeforeEach
    void setUp() {
        tableModel = new BankingTableModelImp();
        ArrayList<BankingTransaction> tableContents = new ArrayList<>();

        Calendar date1 = new GregorianCalendar(2020, Calendar.OCTOBER, 25);
        Calendar date2 = new GregorianCalendar(2020,Calendar.OCTOBER,26);
        Calendar date3 = new GregorianCalendar(2020, Calendar.OCTOBER, 27);

        tableContents.add(new BankingTransaction("3589045", date1, "Amazon", 50F, "Name1", 1000F));
        tableContents.add(new BankingTransaction("3589046", date2, "HEB", 40F, "Name2", 960F));
        tableContents.add(new BankingTransaction("3589047", date3, "Walmart", 30F, "", 930F));

        tableModel.updateTransactions(tableContents);
    }

    @Test
    void getValueAt() {
        String actualDate = (String)tableModel.getValueAt(0,0);
        assertEquals("2020-10-25", actualDate);

        float actualAmount = (float)tableModel.getValueAt(0,1);
        assertEquals(50F, actualAmount);

        String actualCategory = (String)tableModel.getValueAt(0,2);
        assertEquals("Name1", actualCategory);

        String actualDescription = (String)tableModel.getValueAt(0,3);
        assertEquals("Amazon", actualDescription);
    }
}