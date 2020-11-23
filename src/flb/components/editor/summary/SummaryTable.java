package flb.components.editor.summary;

import flb.components.editor.summary.SummarySelector;
import flb.components.editor.transaction.TableHighlighter;
import flb.components.menus.MenuDisplayer;
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
    Maybe<TransactionSummary> getSummary(int row);
    void addEditorMenu (MenuDisplayer menuDisplayer);
}
