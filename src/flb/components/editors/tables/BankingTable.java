package flb.components.editors.tables;

import flb.components.editors.TableHighlighter;
import flb.tuples.*;
import java.util.*;
import flb.util.*;

import javax.swing.*;

public interface BankingTable extends TableHighlighter {
    JScrollPane getPane();
    Maybe<Transaction> getTransaction(int row);
    void display(ArrayList<Transaction> tableContents);
}
