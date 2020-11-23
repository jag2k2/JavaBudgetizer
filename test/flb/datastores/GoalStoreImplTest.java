package flb.datastores;

import static org.junit.jupiter.api.Assertions.*;

import flb.databases.TestDatabase;
import org.junit.jupiter.api.*;
import flb.util.*;
import flb.tuples.*;
import java.util.*;

class GoalStoreImplTest {
    private TestDatabase database;
    private GoalStoreImpl goalStore;
    private WhichMonth whichMonth;

    @BeforeEach
    void setUp() {
        database = new TestDatabase();
        database.connect();
        goalStore = new GoalStoreImpl(database);
        whichMonth = new WhichMonth(2020, Calendar.OCTOBER);
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void createDefaultGoals() {
        WhichMonth septemberDate = new WhichMonth(2020, Calendar.SEPTEMBER);

        goalStore.createDefaultGoals(septemberDate);

        for (Category category : TestDatabase.getTestCategories()){
            assertEquals(category.getDefaultGoal(), goalStore.getGoal(septemberDate, category.getName()));
        }
    }

    @Test
    void deleteGoalDoesntExist() {
        Category categoryWithoutGoal = TestDatabase.getTestCategories().get(1);
        TransactionSummary summaryWithoutGoal = new TransactionSummary(whichMonth, categoryWithoutGoal);

        goalStore.deleteGoal(summaryWithoutGoal);

        Maybe<Float> expected = new Maybe<>();
        assertEquals(expected, goalStore.getGoal(whichMonth, categoryWithoutGoal.getName()));
    }

    @Test
    void deleteGoalDoesExist() {
        Category categoryWithGoal = TestDatabase.getTestCategories().get(0);
        TransactionSummary summaryWithGoal = new TransactionSummary(whichMonth, categoryWithGoal);

        goalStore.deleteGoal(summaryWithGoal);

        Maybe<Float> expected = new Maybe<>();
        assertEquals(expected, goalStore.getGoal(whichMonth, categoryWithGoal.getName()));
    }

    @Test
    void goalExists() {
        Category categoryWithGoal = TestDatabase.getTestCategories().get(0);
        TransactionSummary summaryWithGoal = new TransactionSummary(whichMonth, categoryWithGoal);

        assertTrue(goalStore.goalExists(summaryWithGoal));

        Category categoryWithoutGoal = TestDatabase.getTestCategories().get(1);
        TransactionSummary summaryWithNoGoal = new TransactionSummary(whichMonth, categoryWithoutGoal);
        assertFalse(goalStore.goalExists(summaryWithNoGoal));
    }

    @Test
    void getGoal() {
        Maybe<Float> expected = new Maybe<>(1000F);
        assertEquals(expected, goalStore.getGoal(whichMonth, "Income"));
    }

    @Test
    void updateExistingGoalAmount() {
        Category category = TestDatabase.getTestCategories().get(2);
        TransactionSummary summaryToUpdate = new TransactionSummary(whichMonth, category);
        float newGoal = 200F;
        summaryToUpdate.addGoal(newGoal);

        goalStore.updateGoalAmount(summaryToUpdate);

        Maybe<Float> expected = new Maybe<>(newGoal);
        assertEquals(expected, goalStore.getGoal(whichMonth, category.getName()));
    }

    @Test
    void addGoal() {
        Category categoryToAddTo = TestDatabase.getTestCategories().get(1);
        TransactionSummary summaryToUpdate = new TransactionSummary(whichMonth, categoryToAddTo);
        float newGoal = 200F;
        summaryToUpdate.addGoal(newGoal);

        goalStore.addGoal(summaryToUpdate);

        Maybe<Float> expected = new Maybe<>(newGoal);
        assertEquals(expected, goalStore.getGoal(whichMonth, categoryToAddTo.getName()));
    }

    @Test
    void countCoals() {
        assertEquals(3, goalStore.countGoals(whichMonth));
    }
}