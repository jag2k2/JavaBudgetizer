package flb.components.editors;

import flb.components.editors.tables.*;
import flb.datastores.*;
import flb.tuples.TransactionSummary;
import flb.util.*;
import java.util.*;
import javax.swing.*;

public class GoalEditorImpl implements GoalSelector, MonthChangeListener, StoreChangeListener{
    private final GoalStore goalStore;
    private final TransactionStore transactionStore;
    private final GoalTable goalTable;


    public GoalEditorImpl(TransactionStore transactionStore, GoalStore goalStore){
        this.goalStore = goalStore;
        this.transactionStore = transactionStore;
        GoalTableImp goalTable = new GoalTableImp();
        this.goalTable = goalTable;

    }

    public JScrollPane getPane() {
        return goalTable.getPane();
    }

    public void addGoalSelectedListener(GoalSelectedListener goalSelectedListener){
        goalTable.addGoalSelectedListener(goalSelectedListener);
    }

    @Override
    public void update(WhichMonth whichMonth) {
        ArrayList<TransactionSummary> transactionSummaries = transactionStore.getTransactionSummaries(whichMonth);
        goalTable.display(transactionSummaries);
    }

    @Override
    public void updateAndKeepSelection(WhichMonth whichMonth) {
        ArrayList<TransactionSummary> transactionSummaries = transactionStore.getTransactionSummaries(whichMonth);
        goalTable.displayAndKeepSelection(transactionSummaries);
    }

    @Override
    public Maybe<String> getSelectedGoalName() {
        return goalTable.getSelectedGoalName();
    }
}
