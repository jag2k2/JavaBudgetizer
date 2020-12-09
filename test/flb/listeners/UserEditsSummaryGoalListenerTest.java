package flb.listeners;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.*;
import flb.components.editor.summary.SummaryEditorImpl;
import flb.components.editor.summary.SummaryTableTester;
import flb.components.monthselector.MonthSelectorImpl;
import flb.databases.*;
import flb.util.*;
import flb.datastores.*;
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
        selectedMonth = new WhichMonth(2020, Calendar.OCTOBER);
        database = new TestDatabase();
        database.connect();
        DataStoreImpl dataStoreImpl = new DataStoreImpl(database);
        goalStoreTester = dataStoreImpl;
        MonthSelectorImpl monthSelectorImpl = new MonthSelectorImpl();
        monthSelectorImpl.setYear(2020);
        monthSelectorImpl.setMonth(Calendar.OCTOBER);
        SummaryEditorImpl summaryEditor = new SummaryEditorImpl(dataStoreImpl, monthSelectorImpl);
        tableTester = summaryEditor.getTableTester();
        summaryEditor.updateAndKeepSelection();
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

        String categoryNameToGet = CategoryListFactory.makeDefaultCategories().get(activeRow).getName();
        assertEquals(expected, goalStoreTester.getGoal(selectedMonth, categoryNameToGet));
    }
}