package flb.components.editors;

import flb.components.editors.tables.*;
import flb.components.menus.*;
import flb.components.monthselector.*;
import flb.datastores.*;
import flb.listeners.*;
import flb.tuples.*;
import flb.util.*;
import java.util.*;
import javax.swing.*;

public class SummaryEditorImpl implements MonthGoalEditor, MonthGoalClearer, SummarySelector, ViewChangeObserver,
        StoreChangeObserver, SummaryEditorTester{
    private final GoalStore goalStore;
    private final TransactionStore transactionStore;
    private final SummaryTable summaryTable;
    private final SummaryTableTester tableTester;
    private final SelectedMonthGetter selectedMonthGetter;

    public SummaryEditorImpl(TransactionStore transactionStore, GoalStore goalStore, SelectedMonthGetter selectedMonthGetter, JFrame frame){
        this.goalStore = goalStore;
        this.transactionStore = transactionStore;
        this.selectedMonthGetter = selectedMonthGetter;
        SummaryTableImp goalTable = new SummaryTableImp();
        this.tableTester = goalTable;
        this.summaryTable = goalTable;

        addListeners();
    }

    protected void addListeners() {
        summaryTable.addEditorMenu(new GoalEditorMenuImpl(this));
        summaryTable.addGoalEditedListener(new UserEditsSummaryGoalListener(this));
    }

    public JScrollPane getPane() {
        return summaryTable.getPane();
    }

    public void addGoalSelectedListener(TableHighlighter tableHighlighter){
        summaryTable.addGoalSelectedObserver(tableHighlighter);
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
        }
    }

    @Override
    public void clearGoalAmount(int row) {
        for (TransactionSummary summary : summaryTable.getSummary(row)) {
            goalStore.deleteGoal(summary);
        }
    }

    @Override
    public SummaryTableTester getTableTester() {
        return tableTester;
    }
}
