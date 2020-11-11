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

    @BeforeEach
    void setUp() {
        this.database = new TestDatabase();
        database.connect();
        GoalStoreImpl goalStore = new GoalStoreImpl(database);
        this.goalStoreTester = goalStore;
        TransactionStore transactionStore = new TransactionStoreImp(database);
        SummaryEditorImpl summaryEditor = new SummaryEditorImpl(transactionStore, goalStore, new JFrame());
        this.tableTester = summaryEditor.getTableTester();
        StoreChangeListener storeChangeListener = summaryEditor;
        storeChangeListener.updateAndKeepSelection(new WhichMonth(2020, Calendar.OCTOBER));

        summaryEditor.addGoalEditingListeners();
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

        assertEquals(200F, goalStoreTester.getGoal(new WhichMonth(2020, Calendar.OCTOBER), "Name2"));
    }

    @Test
    void makeNewGoal() {
        tableTester.setSelectedRow(0);

        tableTester.editCellAt(0, 1);
        tableTester.setEditorGoal(200);

        assertEquals(200F, goalStoreTester.getGoal(new WhichMonth(2020, Calendar.OCTOBER), "Name1"));
    }
}