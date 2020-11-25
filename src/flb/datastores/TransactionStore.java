package flb.datastores;

import flb.tuples.*;
import flb.util.WhichMonth;
import java.util.*;

public interface TransactionStore extends StoreChangeNotifier {
    void categorizeTransaction(Transaction transaction, String categoryName);
    void addTransactions(List<? extends Transaction> transactions);
    void labelGroup(List<? extends Transaction> transactions, String groupName);
    ArrayList<BankingTransaction> getBankingTransactions (WhichMonth whichMonth);
    ArrayList<CreditTransaction> getCreditTransactions (WhichMonth whichMonth);
    ArrayList<TransactionSummary> getTransactionSummaries (WhichMonth whichMonth);
}
