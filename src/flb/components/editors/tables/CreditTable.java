package flb.components.editors.tables;

import flb.components.editors.GoalSelectedListener;
import flb.tuples.*;

import java.util.*;
import flb.util.*;

import javax.swing.*;

public interface CreditTable extends GoalSelectedListener {
    JScrollPane getPane();
    void displayAndClearSelection(ArrayList<CreditTransaction> tableContents);
    void displayAndKeepSelection(ArrayList<CreditTransaction> tableContents);
    Maybe<Transaction> getTransaction(int row);
}
