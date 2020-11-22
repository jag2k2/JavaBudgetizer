package flb.components.editors.tables.models;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import flb.databases.TestDatabase;
import flb.tuples.*;
import flb.util.*;


class CreditTableModelImplTest {
    private CreditTableModelImpl tableModel;

    @BeforeEach
    void setUp() {
        tableModel = new CreditTableModelImpl();
        tableModel.updateTransactions(TestDatabase.getTestCreditTransactions());
    }

    @Test
    void getValueAt() {
        int activeRow = 0;
        Transaction expectedTransaction = TestDatabase.getTestCreditTransactions().get(activeRow);

        String expectedDate = expectedTransaction.getDateString();
        String actualDate = (String)tableModel.getValueAt(activeRow,0);
        assertEquals(expectedDate, actualDate);

        float expectedAmount = expectedTransaction.getAmount();
        float actualAmount = (float)tableModel.getValueAt(activeRow,1);
        assertEquals(expectedAmount, actualAmount);

        String expectedCategory = expectedTransaction.getCategoryName();
        String actualCategory = (String)tableModel.getValueAt(0,2);
        assertEquals(expectedCategory, actualCategory);

        String expectedDescription = expectedTransaction.getDescription();
        String actualDescription = (String)tableModel.getValueAt(activeRow,3);
        assertEquals(expectedDescription, actualDescription);
    }

    @Test
    void getTransaction() {
        int activeRow = 0;

        Maybe<Transaction> expected = new Maybe<>(TestDatabase.getTestCreditTransactions().get(activeRow));
        Maybe<Transaction> actual = tableModel.getTransaction(activeRow);
        assertEquals(expected, actual);

        expected = new Maybe<>();
        actual = tableModel.getTransaction(-1);
        assertEquals(expected, actual);
    }
}