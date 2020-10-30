package flb.tables.banking;

import static org.junit.jupiter.api.Assertions.*;
import flb.tables.banking.interfaces.*;
import flb.tuples.*;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

class BankingTableImplTest {
    private BankingTable bankingTable;
    private BankingTableAutomator tableAutomator;
    private ArrayList<BankingTransaction> expectedDisplay;

    @BeforeEach
    void setUp() {
        BankingTableImpl bankingTableImpl = new BankingTableImpl();
        this.bankingTable = bankingTableImpl;
        this.tableAutomator = bankingTableImpl;

        ArrayList<BankingTransaction> tableContents = new ArrayList<>();
        Calendar date1 = new GregorianCalendar(2020, Calendar.OCTOBER, 25);
        Calendar date2 = new GregorianCalendar(2020,Calendar.OCTOBER,26);
        Calendar date3 = new GregorianCalendar(2020, Calendar.OCTOBER, 27);
        tableContents.add(new BankingTransaction("3589045", date1, "Amazon", 50F, "Name1", 1000F));
        tableContents.add(new BankingTransaction("3589046", date2, "HEB", 40F, "Name2", 960F));
        tableContents.add(new BankingTransaction("3589047", date3, "Walmart", 30F, "", 930F));
        bankingTable.refresh(tableContents);

        expectedDisplay = new ArrayList<>();
    }

    @Test
    void refresh() {
        expectedDisplay = new ArrayList<>();

        bankingTable.refresh(expectedDisplay);

        assertEquals(expectedDisplay, tableAutomator.getTransactions());

        Calendar date1 = new GregorianCalendar(2020, Calendar.OCTOBER, 25);
        Calendar date2 = new GregorianCalendar(2020,Calendar.OCTOBER,26);
        expectedDisplay.add(new BankingTransaction("3589045", date1, "Amazon", 50F, "Name1", 1000F));
        expectedDisplay.add(new BankingTransaction("3589046", date2, "HEB", 40F, "Name2", 960F));

        bankingTable.refresh(expectedDisplay);

        assertEquals(expectedDisplay, tableAutomator.getTransactions());
    }
}