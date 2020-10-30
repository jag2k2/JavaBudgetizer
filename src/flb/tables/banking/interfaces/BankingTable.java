package flb.tables.banking.interfaces;

import flb.tuples.BankingTransaction;

import java.awt.event.MouseListener;
import java.util.ArrayList;

public interface BankingTable {
    void display(ArrayList<BankingTransaction> tableContents);
    void addCategoryClickedListener(MouseListener mouseListener);
}
