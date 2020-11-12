package flb.components.editors;

import flb.components.StoreChanger;
import flb.components.editors.tables.*;
import flb.components.menus.*;
import flb.components.monthselector.*;
import flb.datastores.*;
import flb.listeners.*;
import flb.tuples.*;
import flb.util.*;
import java.util.*;
import javax.swing.*;

public class SummaryEditorImpl implements MonthGoalEditor, MonthGoalClearer, DefaultGoalMaker, SummarySelector, StoreChanger,
        MonthChangeObserver, StoreChangeObserver, SummaryEditorTester{
    private final GoalStore goalStore;
    private final TransactionStore transactionStore;
    private final SummaryTable summaryTable;
    private final SummaryTableTester tableTester;
    private final JFrame frame;
    private final ArrayList<StoreChangeObserver> storeChangeObservers;
    private final SelectedMonthGetter selectedMonthGetter;

    public SummaryEditorImpl(TransactionStore transactionStore, GoalStore goalStore, SelectedMonthGetter selectedMonthGetter, JFrame frame){
        this.goalStore = goalStore;
        this.transactionStore = transactionStore;
        this.selectedMonthGetter = selectedMonthGetter;
        SummaryTableImp goalTable = new SummaryTableImp();
        this.tableTester = goalTable;
        this.summaryTable = goalTable;
        this.frame = frame;
        this.storeChangeObservers = new ArrayList<>();

        addListeners();
    }

    protected void addListeners() {
        summaryTable.addEditorMenu(new GoalEditorMenuImpl(this));
        summaryTable.addGoalEditedListener(new UserEditsSummaryGoalListener(this));
    }

    public JScrollPane getPane() {
        return summaryTable.getPane();
    }

    @Override
    public void addStoreChangeObserver(StoreChangeObserver storeChangeObserver){
        storeChangeObservers.add(storeChangeObserver);
    }

    @Override
    public void notifyStoreChange() {
        for(StoreChangeObserver storeChangeObserver : storeChangeObservers) {
            storeChangeObserver.updateAndKeepSelection();
        }
    }

    public void addGoalSelectedListener(TableHighlighter tableHighlighter){
        summaryTable.addGoalSelectedObserver(tableHighlighter);
    }

    @Override
    public void createDefaultGoals(WhichMonth selectedMonth) {
        int goalCount = goalStore.countGoals(selectedMonth);
        if (goalCount > 0) {
            int confirmation = getConfirmationFromDialog(goalCount, frame);
            if(confirmation == JOptionPane.YES_OPTION) {
                goalStore.createDefaultGoals(selectedMonth);
                notifyStoreChange();
            }
        }
        else {
            goalStore.createDefaultGoals(selectedMonth);
            notifyStoreChange();
        }
    }

    protected int getConfirmationFromDialog(int goalCount, JFrame frame) {
        return JOptionPane.showConfirmDialog(frame, "This operation will delete " + goalCount + " existing goals " +
                "for the month.  Are you sure you want to continue?", "Confirm Operation", JOptionPane.YES_NO_OPTION);
    }

    @Override
    public void update() {
        ArrayList<TransactionSummary> transactionSummaries = transactionStore.getTransactionSummaries(selectedMonthGetter.getSelectedMonth());
        summaryTable.display(transactionSummaries);
    }

    @Override
    public void updateAndKeepSelection() {
        ArrayList<TransactionSummary> transactionSummaries = transactionStore.getTransactionSummaries(selectedMonthGetter.getSelectedMonth());
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
            notifyStoreChange();
        }
    }

    @Override
    public void clearGoalAmount(int row) {
        for (TransactionSummary summary : summaryTable.getSummary(row)) {
            goalStore.deleteGoal(summary);
            notifyStoreChange();
        }
    }

    @Override
    public SummaryTableTester getTableTester() {
        return tableTester;
    }
}
