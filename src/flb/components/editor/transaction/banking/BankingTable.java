package flb.components.editor.transaction.banking;

import flb.components.editor.transaction.TableHighlighter;
import flb.tuples.*;
import java.util.*;
import flb.util.*;

import javax.swing.*;

public interface BankingTable extends TableHighlighter {
    JScrollPane getPane();
    Maybe<Transaction> getTransaction(int row);
    void display(ArrayList<Transaction> tableContents);
}
