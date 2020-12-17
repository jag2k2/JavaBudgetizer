package flb.components.editor.transaction.banking;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import flb.databases.DebitListFactory;
import flb.tuples.*;

class BankingTableModelImpTest {
    private BankingTableModelImp tableModel;

    @BeforeEach
    void setUp() {
        tableModel = new BankingTableModelImp();
        tableModel.updateTransactions(DebitListFactory.makeDefaultTransactions());
    }

    @Test
    void getValueAt() {
        int activeRow = 0;
        Transaction expectedTransaction = DebitListFactory.makeDefaultTransactions().get(activeRow);

        String expectedDate = expectedTransaction.getDateString();
        String actualDate = (String)tableModel.getValueAt(activeRow,0);
        assertEquals(expectedDate, actualDate);

        float expectedAmount = expectedTransaction.getAmount();
        float actualAmount = (float)tableModel.getValueAt(activeRow,1);
        assertEquals(expectedAmount, actualAmount);

        String expectedName = expectedTransaction.getCategoryName();
        String actualCategory = (String)tableModel.getValueAt(activeRow,2);
        assertEquals(expectedName, actualCategory);

        String expectedDescription = expectedTransaction.getDescription();
        String actualDescription = (String)tableModel.getValueAt(activeRow,3);
        assertEquals(expectedDescription, actualDescription);
    }

    @Test
    void getTransaction() {
        BankingTransaction expected = DebitListFactory.makeDefaultTransactions().get(0);
        BankingTransaction actual = tableModel.getTransaction(0);
        assertEquals(expected, actual);
    }
}