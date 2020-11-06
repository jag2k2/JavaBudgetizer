package flb.components.editors.tables;

import flb.tuples.TransactionSummary;
import java.util.*;
import javax.swing.*;

public interface GoalTable {
    JScrollPane getPane();
    void display(ArrayList<TransactionSummary> tableContents);
}
