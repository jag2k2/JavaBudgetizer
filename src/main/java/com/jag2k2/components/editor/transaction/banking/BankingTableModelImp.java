package com.jag2k2.components.editor.transaction.banking;

import com.jag2k2.components.editor.transaction.*;
import com.jag2k2.tuples.*;

public class BankingTableModelImp extends AbstractTransactionTableModel<BankingTransaction> {

    public BankingTableModelImp() {
        super("Date", "Amount", "Category", "Description");
    }
}
