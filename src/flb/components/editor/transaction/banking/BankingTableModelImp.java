package flb.components.editor.transaction.banking;

import flb.components.editor.transaction.*;
import flb.tuples.*;

public class BankingTableModelImp extends AbstractTransactionTableModel<BankingTransaction> {

    public BankingTableModelImp() {
        super("Date", "Amount", "Category", "Description");
    }
}
