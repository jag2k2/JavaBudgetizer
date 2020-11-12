package flb.listeners;

import static org.junit.jupiter.api.Assertions.assertEquals;
import flb.components.editors.*;
import flb.components.editors.tables.SummaryTableTester;
import flb.util.WhichMonth;
import org.junit.jupiter.api.*;
import flb.datastores.*;
import javax.swing.*;
import java.util.Calendar;


class UserEditsSummaryGoalListenerTest {
    private TestDatabase database;
    private GoalStoreTester goalStoreTester;
    private SummaryTableTester tableTester;
    private WhichMonth selectedMonth;

    @BeforeEach
    void setUp() {
        this.selectedMonth = new WhichMonth(2020, Calendar.OCTOBER);
        this.database = new TestDatabase();
        database.connect();
        GoalStoreImpl goalStore = new GoalStoreImpl(database);
        this.goalStoreTester = goalStore;
        TransactionStore transactionStore = new TransactionStoreImp(database);
        SummaryEditorImpl summaryEditor = new SummaryEditorImpl(transactionStore, goalStore, new JFrame());
        this.tableTester = summaryEditor.getTableTester();
        StoreChangeListener storeChangeListener = summaryEditor;
        storeChangeListener.updateAndKeepSelection(selectedMonth);
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void editExistingGoal() {
        tableTester.setSelectedRow(1);

        tableTester.editCellAt(1, 1);
        tableTester.setEditorGoal(200);

        assertEquals(200F, goalStoreTester.getGoal(selectedMonth, "Name2"));
    }

    @Test
    void makeNewGoal() {
        int activeRow = 0;
        tableTester.setSelectedRow(activeRow);

        tableTester.editCellAt(activeRow, 1);
        tableTester.setEditorGoal(200);

        String categoryNameToGet = TestDatabase.getTestCategories().get(activeRow).getName();
        assertEquals(200F, goalStoreTester.getGoal(selectedMonth, categoryNameToGet));
    }
}