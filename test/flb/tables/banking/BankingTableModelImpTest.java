package flb.tables.banking;

import static org.junit.jupiter.api.Assertions.*;

import flb.database.TestDatabase;
import org.junit.jupiter.api.*;

class BankingTableModelImpTest {
    private BankingTableModelImp tableModel;

    @BeforeEach
    void setUp() {
        tableModel = new BankingTableModelImp();
        tableModel.updateTransactions(TestDatabase.getTestBankingTransactions());
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