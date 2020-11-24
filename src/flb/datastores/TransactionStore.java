package flb.datastores;

import flb.tuples.*;
import flb.util.WhichMonth;
import java.util.*;

public interface TransactionStore extends StoreChangeNotifier {
    void categorizeTransaction(Transaction transaction, String categoryName);
    void addTransactions(List<Transaction> transactions);
    void labelGroup(List<Transaction> transactions, String groupName);
    ArrayList<Transaction> getBankingTransactions (WhichMonth whichMonth);
    ArrayList<Transaction> getCreditTransactions (WhichMonth whichMonth);
    ArrayList<TransactionSummary> getTransactionSummaries (WhichMonth whichMonth);
}
