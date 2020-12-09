package flb.listeners;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import flb.components.editor.summary.SummaryEditorImpl;
import flb.components.monthselector.MonthSelectorImpl;
import flb.databases.*;
import flb.util.WhichMonth;
import flb.datastores.*;
import flb.tuples.*;
import javax.swing.*;
import java.util.Calendar;

class UserClearsSummaryGoalListenerTest {
    private JButton testButton;
    private TestDatabase database;
    private GoalStore goalStore;
    private int categoryRowWithGoal;
    private WhichMonth whichMonth;

    @BeforeEach
    void setUp() {
        whichMonth = new WhichMonth(2020, Calendar.OCTOBER);
        testButton = new JButton();
        database = new TestDatabase();
        database.connect();
        this.goalStore = new DataStoreImpl(database);

        MonthSelectorImpl monthSelectorImpl = new MonthSelectorImpl();
        monthSelectorImpl.setYear(2020);
        monthSelectorImpl.setMonth(Calendar.OCTOBER);

        SummaryEditorImpl summaryEditor = new SummaryEditorImpl(goalStore, monthSelectorImpl);
        summaryEditor.update();

        testButton.addActionListener(new UserClearsSummaryGoalListener(summaryEditor));
        categoryRowWithGoal = 0;
        testButton.setActionCommand(Integer.toString(categoryRowWithGoal));
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void actionPerformed() {
        Category categoryWithGoal = CategoryListFactory.makeDefaultCategories().get(categoryRowWithGoal);
        TransactionSummary summaryWithGoal = new TransactionSummary(whichMonth, categoryWithGoal);

        testButton.doClick();

        assertFalse(goalStore.goalExists(summaryWithGoal));
    }
}