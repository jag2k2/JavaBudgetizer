package flb.components.editor.transaction.credit;


import flb.components.editor.transaction.TransactionTable;
import flb.tuples.*;
import java.util.*;

public interface CreditTable extends TransactionTable {
    void displayAndKeepSelection(ArrayList<Transaction> tableContents);
}
