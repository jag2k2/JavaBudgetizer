package flb.components.editor.transaction.banking;

import flb.util.Transactions;
import flb.tuples.BankingTransaction;
import flb.components.editor.transaction.*;

public interface BankingTable extends TransactionTable{
    void display(Transactions<BankingTransaction> tableContents);
}
