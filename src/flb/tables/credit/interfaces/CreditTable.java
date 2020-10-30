package flb.tables.credit.interfaces;

import flb.tuples.CreditTransaction;

import java.awt.event.MouseListener;
import java.util.ArrayList;

public interface CreditTable {
    void displayAndClearSelection(ArrayList<CreditTransaction> tableContents);
    void addCategoryClickedListener(MouseListener mouseListener);
}
