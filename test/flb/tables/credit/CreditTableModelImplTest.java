package flb.tables.credit;

import static org.junit.jupiter.api.Assertions.*;
import flb.database.TestDatabase;
import org.junit.jupiter.api.*;

class CreditTableModelImplTest {
    private CreditTableModelImpl tableModel;

    @BeforeEach
    void setUp() {
        tableModel = new CreditTableModelImpl();
        tableModel.updateTransactions(TestDatabase.getTestCreditTransactions());
    }

    @Test
    void getValueAt() {
        String actualDate = (String)tableModel.getValueAt(0,0);
        assertEquals("2020-10-25", actualDate);

        float actualAmount = (float)tableModel.getValueAt(0,1);
        assertEquals(20F, actualAmount);

        String actualDescription = (String)tableModel.getValueAt(0,2);
        assertEquals("Name1", actualDescription);

        String actualCategory = (String)tableModel.getValueAt(0,3);
        assertEquals("Shell", actualCategory);
    }
}