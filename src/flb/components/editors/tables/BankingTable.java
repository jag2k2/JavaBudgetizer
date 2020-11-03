package flb.components.editors.tables;

import flb.tuples.*;
import java.util.*;
import flb.util.*;

import javax.swing.*;

public interface BankingTable {
    JScrollPane getPane();
    void display(ArrayList<BankingTransaction> tableContents);
    Maybe<Transaction>getTransaction(int row);
}
