package flb.components.editor.summary;

import flb.components.menus.MenuDisplayer;
import flb.tuples.*;
import flb.util.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;

public interface SummaryTable extends SummarySelector {
    JScrollPane getPane();
    void display(ArrayList<TransactionSummary> tableContents);
    void displayAndKeepSelection(ArrayList<TransactionSummary> tableContents);
    void addGoalEditedListener(TableModelListener tableModelListener);
    void addGoalSelectionListener(ListSelectionListener listSelectionListener);
    Maybe<TransactionSummary> getSelectedSummary();
    Maybe<TransactionSummary> getSummary(int row);
    void addEditorMenu (MenuDisplayer menuDisplayer);
}
