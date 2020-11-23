package flb.tuples;

import static org.junit.jupiter.api.Assertions.*;

import flb.util.WhichMonth;
import org.junit.jupiter.api.*;

import java.util.Calendar;

class TransactionSummaryTest {
    private TransactionSummary transactionSummary;

    @BeforeEach
    void setUp() {
        transactionSummary = new TransactionSummary(new WhichMonth(2020, Calendar.OCTOBER), new Category("Name1", false));
    }

    @Test
    void testToString() {
        this.transactionSummary.addGoal(100);
        this.transactionSummary.addSum(115);

        String expected = "2020-October | Name1 | [] | false | [100.0] | [115.0]";
        assertEquals(expected, transactionSummary.toString());
    }

    @Test
    void testEquals() {
        this.transactionSummary.addGoal(100);
        this.transactionSummary.addSum(115);

        TransactionSummary summaryToCompare = new TransactionSummary(new WhichMonth(2020, Calendar.OCTOBER), new Category("Name1", false));
        summaryToCompare.addGoal(100);
        summaryToCompare.addSum(115);
        assertEquals(transactionSummary, summaryToCompare);


        summaryToCompare = new TransactionSummary(new WhichMonth(2020, Calendar.SEPTEMBER), new Category("Name1", false));
        summaryToCompare.addGoal(100);
        summaryToCompare.addSum(115);
        assertNotEquals(transactionSummary, summaryToCompare);

        summaryToCompare = new TransactionSummary(new WhichMonth(2020, Calendar.OCTOBER), new Category("Name2", false));
        summaryToCompare.addGoal(100);
        summaryToCompare.addSum(115);
        assertNotEquals(transactionSummary, summaryToCompare);

        summaryToCompare = new TransactionSummary(new WhichMonth(2020, Calendar.OCTOBER), new Category("Name1", false));
        summaryToCompare.addSum(115);
        assertNotEquals(transactionSummary, summaryToCompare);

        summaryToCompare = new TransactionSummary(new WhichMonth(2020, Calendar.OCTOBER), new Category("Name1", false));
        summaryToCompare.addGoal(100);
        assertNotEquals(transactionSummary, summaryToCompare);
    }

    @Test
    void getGoalAmountDefaultNaN(){
        assertEquals(Float.NaN, transactionSummary.getGoalAmountWithDefault(Float.NaN));

        this.transactionSummary.addGoal(100);
        this.transactionSummary.addSum(115);

        assertEquals(100F, transactionSummary.getGoalAmountWithDefault(Float.NaN));
    }

    @Test
    void getSumDefaultNaN(){
        assertEquals(Float.NaN, transactionSummary.getSumWithDefault(Float.NaN));

        this.transactionSummary.addGoal(100);
        this.transactionSummary.addSum(115);

        assertEquals(115F, transactionSummary.getSumWithDefault(Float.NaN));
    }

    @Test
    void getCategoryBalance() {
        assertEquals(0, transactionSummary.getCategoryBalance());
    }
}