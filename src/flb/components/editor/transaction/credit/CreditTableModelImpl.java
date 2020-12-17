package flb.components.editor.transaction.credit;

import flb.components.editor.transaction.*;
import flb.tuples.*;

public class CreditTableModelImpl extends AbstractTransactionTableModel<CreditTransaction> {

    public CreditTableModelImpl() {
        super("Date", "Amount", "Category", "Description", "PayGroup");
    }

    @Override
    public Object getValueAt(int row, int column) {
        if (column == 4) {
            CreditTransaction creditTransaction = super.getTransaction(row).iterator().next();
            return creditTransaction.getPayGroup();
        } else {
            return super.getValueAt(row, column);
        }
    }
}
