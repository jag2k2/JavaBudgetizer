package flb.components.editors.tables;

import flb.components.editors.GoalSelectedListener;
import flb.components.editors.GoalSelector;
import flb.tuples.TransactionSummary;
import java.util.*;
import javax.swing.*;

public interface GoalTable extends GoalSelector {
    JScrollPane getPane();
    void display(ArrayList<TransactionSummary> tableContents);
    void displayAndKeepSelection(ArrayList<TransactionSummary> tableContents);
    void addGoalSelectedListener(GoalSelectedListener goalSelectedListener);
}
