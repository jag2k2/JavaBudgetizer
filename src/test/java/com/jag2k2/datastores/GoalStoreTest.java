package com.jag2k2.datastores;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import com.jag2k2.databases.*;
import com.jag2k2.util.*;
import com.jag2k2.tuples.*;
import java.util.*;

class GoalStoreTest {
    private TestDatabase database;
    private GoalStore goalStore;
    private DefaultGoalStoreCreator goalStoreCreator;
    private GoalStoreTester goalStoreTester;
    private WhichMonth whichMonth;

    @BeforeEach
    void setUp() {
        database = new TestDatabase();
        database.connect();
        DataStoreImpl dataStoreImpl = new DataStoreImpl(database);
        goalStore = dataStoreImpl;
        goalStoreCreator = dataStoreImpl;
        goalStoreTester = dataStoreImpl;
        whichMonth = new WhichMonth(2020, Calendar.OCTOBER);
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void createDefaultGoals() {
        WhichMonth septemberDate = new WhichMonth(2020, Calendar.SEPTEMBER);

        goalStoreCreator.createDefaultGoals(septemberDate);

        for (Category category : CategoryListFactory.makeDefaultCategories()){
            assertEquals(category.getDefaultGoal(), goalStoreTester.getGoal(septemberDate, category.getName()));
        }
    }

    @Test
    void deleteGoalDoesntExist() {
        Category categoryWithoutGoal = CategoryListFactory.makeDefaultCategories().get(1);
        TransactionSummary summaryWithoutGoal = new TransactionSummary(whichMonth, categoryWithoutGoal);

        goalStore.deleteGoal(summaryWithoutGoal);

        Maybe<Float> expected = new Maybe<>();
        assertEquals(expected, goalStoreTester.getGoal(whichMonth, categoryWithoutGoal.getName()));
    }

    @Test
    void deleteGoalDoesExist() {
        Category categoryWithGoal = CategoryListFactory.makeDefaultCategories().get(0);
        TransactionSummary summaryWithGoal = new TransactionSummary(whichMonth, categoryWithGoal);

        goalStore.deleteGoal(summaryWithGoal);

        Maybe<Float> expected = new Maybe<>();
        assertEquals(expected, goalStoreTester.getGoal(whichMonth, categoryWithGoal.getName()));
    }

    @Test
    void goalExists() {
        Category categoryWithGoal = CategoryListFactory.makeDefaultCategories().get(0);
        TransactionSummary summaryWithGoal = new TransactionSummary(whichMonth, categoryWithGoal);

        assertTrue(goalStore.goalExists(summaryWithGoal));

        Category categoryWithoutGoal = CategoryListFactory.makeDefaultCategories().get(1);
        TransactionSummary summaryWithNoGoal = new TransactionSummary(whichMonth, categoryWithoutGoal);
        assertFalse(goalStore.goalExists(summaryWithNoGoal));
    }

    @Test
    void getGoal() {
        Maybe<Float> expected = new Maybe<>(1000F);
        assertEquals(expected, goalStoreTester.getGoal(whichMonth, "Income"));
    }

    @Test
    void updateExistingGoalAmount() {
        Category category = CategoryListFactory.makeDefaultCategories().get(2);
        TransactionSummary summaryToUpdate = new TransactionSummary(whichMonth, category);
        float newGoal = 200F;
        summaryToUpdate.addGoal(newGoal);

        goalStore.updateGoalAmount(summaryToUpdate);

        Maybe<Float> expected = new Maybe<>(newGoal);
        assertEquals(expected, goalStoreTester.getGoal(whichMonth, category.getName()));
    }

    @Test
    void addGoal() {
        Category categoryToAddTo = CategoryListFactory.makeDefaultCategories().get(1);
        TransactionSummary summaryToUpdate = new TransactionSummary(whichMonth, categoryToAddTo);
        float newGoal = 200F;
        summaryToUpdate.addGoal(newGoal);

        goalStore.addGoal(summaryToUpdate);

        Maybe<Float> expected = new Maybe<>(newGoal);
        assertEquals(expected, goalStoreTester.getGoal(whichMonth, categoryToAddTo.getName()));
    }

    @Test
    void countCoals() {
        assertEquals(3, goalStoreCreator.countGoals(whichMonth));
    }
}