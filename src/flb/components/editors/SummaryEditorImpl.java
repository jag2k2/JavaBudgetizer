package flb.components.editors;

import flb.components.editors.tables.*;
import flb.components.menus.*;
import flb.datastores.*;
import flb.listeners.UserEditsSummaryGoalListener;
import flb.tuples.TransactionSummary;
import flb.util.*;
import java.util.*;
import javax.swing.*;

public class SummaryEditorImpl implements MonthGoalEditor, MonthGoalClearer, DefaultGoalMaker, SummarySelector,
        MonthChangeListener, StoreChangeListener, SummaryEditorTester{
    private final GoalStore goalStore;
    private final TransactionStore transactionStore;
    private final SummaryTable summaryTable;
    private final SummaryTableTester tableTester;
    private final JFrame frame;
    private final ArrayList<StoreChangeListener> storeChangeListeners;

    public SummaryEditorImpl(TransactionStore transactionStore, GoalStore goalStore, JFrame frame){
        this.goalStore = goalStore;
        this.transactionStore = transactionStore;
        SummaryTableImp goalTable = new SummaryTableImp();
        this.tableTester = goalTable;
        this.summaryTable = goalTable;
        this.frame = frame;
        this.storeChangeListeners = new ArrayList<>();

        addListeners();
    }

    protected void addListeners() {
        summaryTable.addEditorMenu(new GoalEditorMenuImpl(this));
        summaryTable.addGoalEditedListener(new UserEditsSummaryGoalListener(this));
    }

    public void addGoalChangeListeners(StoreChangeListener storeChangeListener){
        storeChangeListeners.add(storeChangeListener);
    }

    protected void notifyGoalChanged(WhichMonth selectedMonth) {
        for(StoreChangeListener storeChangeListener : storeChangeListeners) {
            storeChangeListener.updateAndKeepSelection(selectedMonth);
        }
    }

    public JScrollPane getPane() {
        return summaryTable.getPane();
    }

    public void addGoalSelectedListener(TableHighlighter tableHighlighter){
        summaryTable.addGoalSelectedObserver(tableHighlighter);
    }

    public void createDefaultGoals(WhichMonth selectedMonth) {
        int goalCount = goalStore.countGoals(selectedMonth);
        if (goalCount > 0) {
            int confirmation = getConfirmationFromDialog(goalCount, frame);
            if(confirmation == JOptionPane.YES_OPTION) {
                goalStore.createDefaultGoals(selectedMonth);
                notifyGoalChanged(selectedMonth);
            }
        }
        else {
            goalStore.createDefaultGoals(selectedMonth);
            notifyGoalChanged(selectedMonth);
        }
    }

    protected int getConfirmationFromDialog(int goalCount, JFrame frame) {
        return JOptionPane.showConfirmDialog(frame, "This operation will delete " + goalCount + " existing goals " +
                "for the month.  Are you sure you want to continue?", "Confirm Operation", JOptionPane.YES_NO_OPTION);
    }

    @Override
    public void update(WhichMonth whichMonth) {
        ArrayList<TransactionSummary> transactionSummaries = transactionStore.getTransactionSummaries(whichMonth);
        summaryTable.display(transactionSummaries);
    }

    @Override
    public void updateAndKeepSelection(WhichMonth whichMonth) {
        ArrayList<TransactionSummary> transactionSummaries = transactionStore.getTransactionSummaries(whichMonth);
        summaryTable.displayAndKeepSelection(transactionSummaries);
    }

    @Override
    public Maybe<String> getSelectedGoalName() {
        return summaryTable.getSelectedGoalName();
    }

    @Override
    public void updateSelectedGoalAmount() {
        for (TransactionSummary summary : summaryTable.getSelectedSummary()) {
            if(goalStore.goalExists(summary)) {
                goalStore.updateGoalAmount(summary);
            }
            else {
                goalStore.addGoal(summary);
            }
            notifyGoalChanged(summary.getMonth());
        }
    }

    @Override
    public void clearGoalAmount(int row) {
        for (TransactionSummary summary : summaryTable.getSummary(row)) {
            goalStore.deleteGoal(summary);
            notifyGoalChanged(summary.getMonth());
        }
    }

    @Override
    public SummaryTableTester getTableTester() {
        return tableTester;
    }
}
