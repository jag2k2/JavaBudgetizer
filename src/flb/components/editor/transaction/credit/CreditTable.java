package flb.components.editor.transaction.credit;


import flb.components.editor.transaction.TransactionTable;
import flb.tuples.*;
import java.util.*;

public interface CreditTable extends TransactionTable {
    void display(ArrayList<CreditTransaction> tableContents);
    void displayAndKeepSelection(ArrayList<CreditTransaction> tableContents);
    List<CreditTransaction> getSelectedTransactions();
    float getSelectedSum();
}
