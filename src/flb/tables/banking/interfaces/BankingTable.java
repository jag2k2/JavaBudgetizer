package flb.tables.banking.interfaces;

import flb.tuples.BankingTransaction;

import java.util.ArrayList;

public interface BankingTable {
    void refresh(ArrayList<BankingTransaction> tableContents);
}
