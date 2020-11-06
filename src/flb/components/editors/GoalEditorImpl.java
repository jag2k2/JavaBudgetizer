package flb.components.editors;

import flb.components.editors.tables.*;
import flb.datastores.*;
import flb.tuples.TransactionSummary;
import flb.util.WhichMonth;
import java.util.*;
import javax.swing.*;

public class GoalEditorImpl implements MonthChangeListener, StoreChangeListener{
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

    @Override
    public void update(WhichMonth whichMonth) {
        ArrayList<TransactionSummary> transactionSummaries = transactionStore.getTransactionSummaries(whichMonth);
        goalTable.display(transactionSummaries);
    }
}
