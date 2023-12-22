package com.jag2k2.components.editor.transaction.credit;

import com.jag2k2.components.editor.transaction.*;
import com.jag2k2.tuples.*;

public class CreditTableModelImpl extends AbstractTransactionTableModel<CreditTransaction> {

    public CreditTableModelImpl() {
        super("Date", "Amount", "Category", "Description", "PayGroup");
    }

    @Override
    public Object getValueAt(int row, int column) {
        if (column == 4) {
            CreditTransaction creditTransaction = super.getTransaction(row);
            return creditTransaction.getPayGroup();
        } else {
            return super.getValueAt(row, column);
        }
    }
}
