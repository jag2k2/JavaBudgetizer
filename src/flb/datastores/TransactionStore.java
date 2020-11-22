package flb.datastores;

import flb.tuples.Transaction;
import flb.tuples.TransactionSummary;
import flb.util.WhichMonth;
import java.util.*;

public interface TransactionStore extends StoreChangeNotifier {
    void categorizeTransaction(Transaction transaction, String categoryName);
    void addTransactions(List<Transaction> transactions);
    ArrayList<Transaction> getBankingTransactions (WhichMonth whichMonth);
    ArrayList<Transaction> getCreditTransactions (WhichMonth whichMonth);
    ArrayList<TransactionSummary> getTransactionSummaries (WhichMonth whichMonth);
}
