package flb.tables.banking.interfaces;

import flb.tuples.BankingTransaction;
import java.util.ArrayList;

public interface BankingTableTester {
    ArrayList<BankingTransaction> getTransactions();
}
