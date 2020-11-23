package flb.components.editor.transaction.credit;

import flb.components.editor.transaction.TableHighlighter;
import flb.tuples.*;

import java.util.*;
import flb.util.*;

import javax.swing.*;

public interface CreditTable extends TableHighlighter {
    JScrollPane getPane();
    Maybe<Transaction> getTransaction(int row);
    void displayAndClearSelection(ArrayList<Transaction> tableContents);
    void displayAndKeepSelection(ArrayList<Transaction> tableContents);
}
