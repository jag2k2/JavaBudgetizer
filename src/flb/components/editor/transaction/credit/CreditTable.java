package flb.components.editor.transaction.credit;

import flb.components.editor.transaction.TransactionTable;
import flb.util.Transactions;
import flb.tuples.*;

public interface CreditTable extends TransactionTable<CreditTransaction> {
    void displayAndKeepSelection(Transactions<CreditTransaction> tableContents);
    Transactions<CreditTransaction> getSelectedTransactions();
    float getSelectedSum();
}
