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
    List<Goal> expected;
    List<Goal> actual;

    @BeforeEach
    void setUp() {
        expected = new ArrayList<>();
        actual = new ArrayList<>();
        database = new TestDatabase();
        database.connect();
        GoalStoreImpl goalStore = new GoalStoreImpl(database);
        goalStoreTester = goalStore;
        this.goalStore = goalStore;
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void getGoals() {
        expected = TestDatabase.getTestGoals();
        expected = expected.subList(0,3);

        actual = goalStoreTester.getGoals(new WhichMonth(2020, Calendar.SEPTEMBER));

        assertEquals(expected, actual);
    }

    @Test
    void createDefaultGoals() {
        WhichMonth date = new WhichMonth(2020, Calendar.SEPTEMBER);
        expected = new ArrayList<>();
        for (Category category : TestDatabase.getTestCategories()){
            if (!Float.isNaN(category.getDefaultGoal())){
                expected.add(new Goal(date, category, category.getDefaultGoal()));
            }
        }

        goalStore.createDefaultGoals(date);

        assertEquals(expected, goalStoreTester.getGoals(date));
    }

    @Test
    void countGoals() {
        WhichMonth date = new WhichMonth(2020, Calendar.OCTOBER);

        int actual = goalStore.countGoals(date);

        assertEquals(3, actual);

    }

    @Test
    void addGoal() {
    }

    @Test
    void deleteGoal() {
    }

    @Test
    void goalExists() {
    }

    @Test
    void updateGoalAmount() {
    }
}