package com.jag2k2.components.editor.transaction.credit;

import com.jag2k2.components.editor.transaction.TransactionTableTester;

public interface CreditTableTester extends TransactionTableTester {
    void setSelectedRows(int[] selectedRows);
    int[] getSelectedRows();
    String getStatusText();
}
