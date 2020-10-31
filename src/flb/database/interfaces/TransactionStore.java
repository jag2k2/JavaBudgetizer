package flb.database.interfaces;

import flb.tuples.BankingTransaction;
import flb.tuples.CreditTransaction;
import flb.tuples.Transaction;
import flb.util.WhichMonth;

import java.util.ArrayList;

public interface TransactionStore {
    ArrayList<BankingTransaction> getBankingTransactions (WhichMonth searchMonth);
    ArrayList<CreditTransaction> getCreditTransactions (WhichMonth searchMonth);
    void categorizeTransaction(Transaction transaction, String categoryName);
}
