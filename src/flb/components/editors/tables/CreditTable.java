package flb.components.editors.tables;

import flb.tuples.*;

import java.util.*;
import flb.util.*;

import javax.swing.*;

public interface CreditTable {
    JScrollPane getPane();
    void displayAndClearSelection(ArrayList<CreditTransaction> tableContents);
    Maybe<Transaction>getTransaction(int row);
}
