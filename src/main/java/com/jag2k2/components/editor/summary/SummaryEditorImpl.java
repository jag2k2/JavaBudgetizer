package com.jag2k2.components.editor.summary;

import com.jag2k2.components.editor.*;
import com.jag2k2.components.editor.transaction.TableHighlighter;
import com.jag2k2.components.menus.*;
import com.jag2k2.components.monthselector.*;
import com.jag2k2.datastores.*;
import com.jag2k2.listeners.*;
import com.jag2k2.tuples.*;
import com.jag2k2.util.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class SummaryEditorImpl extends JComponent implements MonthGoalEditor, MonthGoalClearer, SummarySelector, ViewChangeObserver,
        StoreChangeObserver, SummaryEditorTester, GoalSelectedNotifier{
    private final GoalStore goalStore;
    private final SummaryTable summaryTable;
    private final SummaryTableTester tableTester;
    private final MonthDisplay monthDisplay;
    private final List<TableHighlighter> tableHighlighters;

    public SummaryEditorImpl(GoalStore goalStore, MonthDisplay monthDisplay){
        this.goalStore = goalStore;
        this.monthDisplay = monthDisplay;
        SummaryTableImp summaryTableImp = new SummaryTableImp();
        this.tableTester = summaryTableImp;
        this.summaryTable = summaryTableImp;
        this.tableHighlighters = new ArrayList<>();

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
        ArrayList<TransactionSummary> transactionSummaries = goalStore.getTransactionSummaries(monthDisplay.getMonth());
        summaryTable.display(transactionSummaries);
    }

    @Override
    public void updateAndKeepSelection() {
        ArrayList<TransactionSummary> transactionSummaries = goalStore.getTransactionSummaries(monthDisplay.getMonth());
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
