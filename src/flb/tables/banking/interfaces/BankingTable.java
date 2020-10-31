package flb.tables.banking.interfaces;

import flb.tuples.*;
import java.awt.event.*;
import java.util.*;
import flb.util.*;

public interface BankingTable {
    void display(ArrayList<BankingTransaction> tableContents);
    void addCategoryClickedListener(MouseListener mouseListener);
    Maybe<Transaction>getTransaction(int row);
}
