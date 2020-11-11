package flb.components.editors.tables;

import flb.components.editors.*;
import flb.tuples.*;
import flb.util.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.TableModelListener;

public interface SummaryTable extends SummarySelector {
    JScrollPane getPane();
    void display(ArrayList<TransactionSummary> tableContents);
    void displayAndKeepSelection(ArrayList<TransactionSummary> tableContents);
    void addGoalSelectedObserver(TableHighlighter tableHighlighter);
    void addGoalEditedListener(TableModelListener tableModelListener);
    Maybe<TransactionSummary> getSelectedSummary();
}
