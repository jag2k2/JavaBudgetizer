package flb.components.editors.tables.models;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import flb.datastores.TestDatabase;
import flb.tuples.*;
import flb.util.*;

class BankingTableModelImpTest {
    private BankingTableModelImp tableModel;

    @BeforeEach
    void setUp() {
        tableModel = new BankingTableModelImp();
        tableModel.updateTransactions(TestDatabase.getTestBankingTransactions());
    }

    @Test
    void getValueAt() {
        int activeRow = 0;
        BankingTransaction expectedTransaction = TestDatabase.getTestBankingTransactions().get(activeRow);

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
        Maybe<Transaction> expected = new Maybe<>(TestDatabase.getTestBankingTransactions().get(0));
        Maybe<Transaction> actual = tableModel.getTransaction(0);
        assertEquals(expected, actual);

        expected = new Maybe<>();
        actual = tableModel.getTransaction(-1);
        assertEquals(expected, actual);
    }
}