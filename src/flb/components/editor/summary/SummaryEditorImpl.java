package flb.components.editor.summary;

import flb.components.editor.*;
import flb.components.editor.transaction.TableHighlighter;
import flb.components.menus.*;
import flb.components.monthselector.*;
import flb.datastores.*;
import flb.listeners.*;
import flb.tuples.*;
import flb.util.*;

import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class SummaryEditorImpl extends JComponent implements MonthGoalEditor, MonthGoalClearer, SummarySelector, ViewChangeObserver,
        StoreChangeObserver, SummaryEditorTester, GoalSelectedNotifier{
    private final GoalStore goalStore;
    private final TransactionStore transactionStore;
    private final SummaryTable summaryTable;
    private final SummaryTableTester tableTester;
    private final MonthDisplay monthDisplay;
    private final List<TableHighlighter> tableHighlighters;

    public SummaryEditorImpl(TransactionStore transactionStore, CategoryStore categoryStore, GoalStore goalStore, MonthDisplay monthDisplay){
        this.goalStore = goalStore;
        this.transactionStore = transactionStore;
        this.monthDisplay = monthDisplay;
        SummaryTableImp summaryTableImp = new SummaryTableImp();
        this.tableTester = summaryTableImp;
        this.summaryTable = summaryTableImp;
        this.tableHighlighters = new ArrayList<>();

        transactionStore.addStoreChangeObserver(this);
        categoryStore.addStoreChangeObserver(this);
        goalStore.addStoreChangeObserver(this);
        monthDisplay.addViewChangeObserver(this);

        this.setLayout(new BorderLayout());
        this.add(summaryTableImp);
        addListeners();
    }

    protected void addListeners() {
        summaryTable.addEditorMenu(new GoalEditorMenuImpl(this));
        summaryTable.addGoalEditedListener(new UserEditsSummaryGoalListener(this));
        summaryTable.addGoalSelectionListener(new UserSelectsGoalListener(this));
    }

    @Override
    public void addGoalSelectedObserver(TableHighlighter tableHighlighter){
        tableHighlighters.add(tableHighlighter);
    }

    @Override
    public void notifyGoalSelected(){
        for(TableHighlighter tableHighlighter : tableHighlighters){
            tableHighlighter.highlightRows();
        }
    }

    @Override
    public void update() {
        ArrayList<TransactionSummary> transactionSummaries = transactionStore.getTransactionSummaries(monthDisplay.getMonth());
        summaryTable.display(transactionSummaries);
    }

    @Override
    public void updateAndKeepSelection() {
        ArrayList<TransactionSummary> transactionSummaries = transactionStore.getTransactionSummaries(monthDisplay.getMonth());
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
