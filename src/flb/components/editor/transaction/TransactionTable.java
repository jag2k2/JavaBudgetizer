package flb.components.editor.transaction;

import flb.tuples.*;
import java.util.*;
import flb.util.*;
import javax.swing.*;

public interface TransactionTable extends TableHighlighter {
    JPanel getPanel();
    Maybe<Transaction> getTransaction(int row);
    void display(ArrayList<Transaction> tableContents);
}
