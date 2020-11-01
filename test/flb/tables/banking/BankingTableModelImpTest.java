package flb.tables.banking;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import flb.database.*;
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
        String actualDate = (String)tableModel.getValueAt(0,0);
        assertEquals("2020-10-25", actualDate);

        float actualAmount = (float)tableModel.getValueAt(0,1);
        assertEquals(50F, actualAmount);

        String actualCategory = (String)tableModel.getValueAt(0,2);
        assertEquals("Name1", actualCategory);

        String actualDescription = (String)tableModel.getValueAt(0,3);
        assertEquals("Amazon", actualDescription);
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