package flb.components.editors;

import flb.components.editors.tables.*;
import flb.datastores.*;
import flb.tuples.TransactionSummary;
import flb.util.*;
import java.util.*;
import javax.swing.*;

public class GoalEditorImpl implements DefaultGoalMaker, GoalSelector, MonthChangeListener, StoreChangeListener{
    private final GoalStore goalStore;
    private final TransactionStore transactionStore;
    private final GoalTable goalTable;
    private final JFrame frame;


    public GoalEditorImpl(TransactionStore transactionStore, GoalStore goalStore, JFrame frame){
        this.goalStore = goalStore;
        this.transactionStore = transactionStore;
        GoalTableImp goalTable = new GoalTableImp();
        this.goalTable = goalTable;
        this.frame = frame;

    }

    public JScrollPane getPane() {
        return goalTable.getPane();
    }

    public void addGoalSelectedListener(GoalSelectedListener goalSelectedListener){
        goalTable.addGoalSelectedListener(goalSelectedListener);
    }

    public void createDefaultgoals(WhichMonth selectedMonth) {
        int goalCount = goalStore.countGoals(selectedMonth);
        if (goalCount > 0) {
            int confirmation = getConfirmationFromDialog(goalCount, frame);
            if(confirmation == JOptionPane.YES_OPTION) {
                goalStore.createDefaultGoals(selectedMonth);
                update(selectedMonth);
            }
        }
        else {
            goalStore.createDefaultGoals(selectedMonth);
            update(selectedMonth);
        }
    }

    protected int getConfirmationFromDialog(int goalCount, JFrame frame) {
        return JOptionPane.showConfirmDialog(frame, "This operation will delete " + goalCount + " existing goals " +
                "for the month.  Are you sure you want to continue?", "Confirm Operation", JOptionPane.YES_NO_OPTION);
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
