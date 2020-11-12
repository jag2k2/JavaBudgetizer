package flb.datastores;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import flb.util.*;
import flb.tuples.*;
import java.util.*;

class GoalStoreImplTest {
    private TestDatabase database;
    private GoalStoreTester goalStoreTester;
    private GoalStore goalStore;
    private WhichMonth selectedDate;
    List<TransactionSummary> expected;
    List<TransactionSummary> actual;

    @BeforeEach
    void setUp() {
        expected = new ArrayList<>();
        actual = new ArrayList<>();
        database = new TestDatabase();
        database.connect();
        GoalStoreImpl goalStore = new GoalStoreImpl(database);
        goalStoreTester = goalStore;
        this.goalStore = goalStore;
        selectedDate = new WhichMonth(2020, Calendar.OCTOBER);
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    /*@Test
    void createDefaultGoals() {
        WhichMonth date = new WhichMonth(2020, Calendar.SEPTEMBER);
        expected = new ArrayList<>();
        for (Category category : TestDatabase.getTestCategories()){
            if (!Float.isNaN(category.getDefaultGoal())){
                expected.add(new Goal(date, category.getDefaultGoal()));
            }
        }

        goalStore.createDefaultGoals(date);

        assertEquals(expected, goalStoreTester.getGoals(date));
    }*/

    /*@Test
    void countGoals() {
        int actual = goalStore.countGoals(selectedDate);

        assertEquals(3, actual);
    }

    @Test
    void addGoal() {
        Goal goaltoAdd = new Goal(selectedDate, 42);
        expected = TestDatabase.getTestGoals().subList(3,6);
        expected.add(0, goaltoAdd);

        goalStore.addGoal("Name1", goaltoAdd);

        assertEquals(expected, goalStoreTester.getGoals(selectedDate));
    }

    @Test
    void deleteGoalDoesntExist() {
        expected = TestDatabase.getTestGoals().subList(3,6);

        Goal goalToDelete = new Goal(selectedDate, 0);
        goalStore.deleteGoal("Name10", goalToDelete);

        assertEquals(expected, goalStoreTester.getGoals(selectedDate));
    }

    @Test
    void deleteGoalDoesExist() {
        expected = TestDatabase.getTestGoals().subList(3,6);
        expected.remove(0);

        Goal goalToDelete = TestDatabase.getTestGoals().get(3);
        goalStore.deleteGoal("Name2", goalToDelete);

        assertEquals(expected, goalStoreTester.getGoals(selectedDate));
    }

    @Test
    void goalExists() {
        assertFalse(goalStore.goalExists("Name1", selectedDate));

        assertTrue(goalStore.goalExists("Name2", selectedDate));
    }*/

    @Test
    void getGoal() {
        assertEquals(1000, goalStoreTester.getGoal(selectedDate, "Income"));
    }

    @Test
    void updateExistingGoalAmount() {
        Category category = TestDatabase.getTestCategories().get(2);
        TransactionSummary summaryToUpdate = new TransactionSummary(selectedDate, category);
        summaryToUpdate.addGoal(200);

        goalStore.updateGoalAmount(summaryToUpdate);

        assertEquals(200, goalStoreTester.getGoal(selectedDate, category.getName()));
    }

    @Test
    void addGoal() {
        Category categoryToAddTo = TestDatabase.getTestCategories().get(1);
        TransactionSummary summaryToUpdate = new TransactionSummary(selectedDate, categoryToAddTo);
        summaryToUpdate.addGoal(200);

        goalStore.addGoal(summaryToUpdate);

        assertEquals(200, goalStoreTester.getGoal(selectedDate, categoryToAddTo.getName()));
    }
}