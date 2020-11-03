package flb.tables.banking.interfaces;

import flb.tuples.*;
import java.util.*;
import flb.util.*;

import javax.swing.*;

public interface BankingTable {
    JScrollPane getPane();
    void display(ArrayList<BankingTransaction> tableContents);
    Maybe<Transaction>getTransaction(int row);
}
