package com.jag2k2.components.editor.transaction.credit;

import com.jag2k2.components.editor.transaction.TransactionTable;
import com.jag2k2.util.Transactions;
import com.jag2k2.tuples.*;

public interface CreditTable extends TransactionTable<CreditTransaction> {
    void displayAndKeepSelection(Transactions<CreditTransaction> tableContents);
    Transactions<CreditTransaction> getSelectedTransactions();
    float getSelectedSum();
}
