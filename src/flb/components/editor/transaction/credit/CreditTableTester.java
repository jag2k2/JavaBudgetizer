package flb.components.editor.transaction.credit;

import flb.components.editor.transaction.TransactionTableTester;

public interface CreditTableTester extends TransactionTableTester {
    void setSelectedRows(int[] selectedRows);
    int[] getSelectedRows();
    String getStatusText();
}
