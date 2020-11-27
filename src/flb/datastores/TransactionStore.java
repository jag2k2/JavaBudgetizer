package flb.datastores;

import flb.tuples.*;
import flb.util.WhichMonth;
import flb.util.Transactions;
import java.util.*;

public interface TransactionStore extends StoreChangeNotifier {
    void categorizeTransaction(Transaction transaction, String categoryName);
    void addTransactions(Transactions<? extends Transaction> transactions);
    void labelGroup(Transactions<? extends Transaction> transactions, String groupName);
    Transactions<BankingTransaction> getBankingTransactions (WhichMonth whichMonth);
    Transactions<CreditTransaction> getCreditTransactions (WhichMonth whichMonth);
    ArrayList<TransactionSummary> getTransactionSummaries (WhichMonth whichMonth);
}
