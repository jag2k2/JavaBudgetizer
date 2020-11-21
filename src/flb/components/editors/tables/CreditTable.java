package flb.components.editors.tables;

import flb.components.editors.TableHighlighter;
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
