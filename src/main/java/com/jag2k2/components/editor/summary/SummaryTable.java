package com.jag2k2.components.editor.summary;

import com.jag2k2.components.menus.MenuDisplayer;
import com.jag2k2.tuples.*;
import com.jag2k2.util.*;
import java.util.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;

public interface SummaryTable {
    void display(ArrayList<TransactionSummary> tableContents);
    void displayAndKeepSelection(ArrayList<TransactionSummary> tableContents);
    void addGoalEditedListener(TableModelListener tableModelListener);
    void addGoalSelectionListener(ListSelectionListener listSelectionListener);
    Maybe<TransactionSummary> getSelectedSummary();
    Maybe<TransactionSummary> getSummary(int row);
    void addEditorMenu (MenuDisplayer menuDisplayer);
    Maybe<String> getSelectedGoalName();
}
