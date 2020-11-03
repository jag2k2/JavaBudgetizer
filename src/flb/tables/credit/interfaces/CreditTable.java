package flb.tables.credit.interfaces;

import flb.tuples.*;
import java.awt.event.MouseListener;
import java.util.*;
import flb.util.*;

public interface CreditTable {
    void displayAndClearSelection(ArrayList<CreditTransaction> tableContents);
    void addCategoryColumnClickedListener(MouseListener mouseListener);
    Maybe<Transaction>getTransaction(int row);
}
