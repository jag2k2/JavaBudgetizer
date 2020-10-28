package flb.tables.credit;

import flb.tuples.CreditTransaction;
import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import static org.junit.jupiter.api.Assertions.*;

class CreditTableModelImpTest {
    private CreditTableModelImp tableModel;

    @BeforeEach
    void setUp() {
        tableModel = new CreditTableModelImp();
        ArrayList<CreditTransaction> tableContents = new ArrayList<>();

        Calendar date1 = new GregorianCalendar(2020, Calendar.OCTOBER, 25);
        Calendar date2 = new GregorianCalendar(2020,Calendar.OCTOBER,26);
        Calendar date3 = new GregorianCalendar(2020, Calendar.OCTOBER, 27);

        tableContents.add(new CreditTransaction("3589045", date1, "Amazon", 50F, "Name1"));
        tableContents.add(new CreditTransaction("3589046", date2, "HEB", 40F, "Name2"));
        tableContents.add(new CreditTransaction("3589047", date3, "Walmart", 30F, ""));

        tableModel.updateTransactions(tableContents);
    }

    @Test
    void getValueAt() {
        String actualDate = (String)tableModel.getValueAt(0,0);
        assertEquals("2020-10-25", actualDate);

        float actualAmount = (float)tableModel.getValueAt(0,1);
        assertEquals(50F, actualAmount);

        String actualDescription = (String)tableModel.getValueAt(0,2);
        assertEquals("Name1", actualDescription);

        String actualCategory = (String)tableModel.getValueAt(0,3);
        assertEquals("Amazon", actualCategory);
    }
}