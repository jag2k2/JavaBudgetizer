package flb.components.editor.transaction.credit;


import flb.components.editor.transaction.TransactionTable;
import flb.util.Transactions;
import flb.tuples.*;

public interface CreditTable extends TransactionTable {
    void display(Transactions<CreditTransaction> tableContents);
    void displayAndKeepSelection(Transactions<CreditTransaction> tableContents);
    Transactions<CreditTransaction> getSelectedTransactions();
    float getSelectedSum();
}
