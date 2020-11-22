package flb.listeners;

import static org.junit.jupiter.api.Assertions.assertEquals;
import flb.components.editors.*;
import flb.components.editors.tables.SummaryTableTester;
import flb.components.monthselector.MonthSelectorImpl;
import flb.databases.TestDatabase;
import flb.util.*;
import org.junit.jupiter.api.*;
import flb.datastores.*;
import javax.swing.*;
import java.util.Calendar;


class UserEditsSummaryGoalListenerTest {
    private TestDatabase database;
    private GoalStoreTester goalStoreTester;
    private SummaryTableTester tableTester;
    private WhichMonth selectedMonth;
    private Float newGoal;
    private Maybe<Float> expected;

    @BeforeEach
    void setUp() {
        this.selectedMonth = new WhichMonth(2020, Calendar.OCTOBER);
        this.database = new TestDatabase();
        database.connect();
        GoalStoreImpl goalStore = new GoalStoreImpl(database);
        this.goalStoreTester = goalStore;
        TransactionStore transactionStore = new TransactionStoreImp(database);
        MonthSelectorImpl monthSelectorImpl = new MonthSelectorImpl();
        monthSelectorImpl.setYear(2020);
        monthSelectorImpl.setMonth(Calendar.OCTOBER);
        SummaryEditorImpl summaryEditor = new SummaryEditorImpl(transactionStore, goalStore, monthSelectorImpl, new JFrame());
        this.tableTester = summaryEditor.getTableTester();
        StoreChangeObserver storeChangeObserver = summaryEditor;
        storeChangeObserver.updateAndKeepSelection();
        this.newGoal = 200F;
        this.expected = new Maybe<>(newGoal);
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void editExistingGoal() {
        tableTester.setSelectedRow(1);

        tableTester.editCellAt(1, 1);
        tableTester.setEditorGoal(newGoal);

        assertEquals(expected, goalStoreTester.getGoal(selectedMonth, "Name2"));
    }

    @Test
    void makeNewGoal() {
        int activeRow = 0;
        tableTester.setSelectedRow(activeRow);

        tableTester.editCellAt(activeRow, 1);
        tableTester.setEditorGoal(newGoal);

        String categoryNameToGet = TestDatabase.getTestCategories().get(activeRow).getName();
        assertEquals(expected, goalStoreTester.getGoal(selectedMonth, categoryNameToGet));
    }
}