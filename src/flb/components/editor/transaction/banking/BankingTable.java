package flb.components.editor.transaction.banking;

import flb.tuples.BankingTransaction;
import flb.components.editor.transaction.*;
import java.util.ArrayList;

public interface BankingTable extends TransactionTable{
    void display(ArrayList<BankingTransaction> tableContents);
}
