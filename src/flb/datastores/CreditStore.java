package flb.datastores;

import flb.tuples.*;
import flb.util.WhichMonth;
import flb.util.Transactions;
import java.util.ArrayList;

public interface CreditStore extends StoreChangeNotifier {
    ArrayList<Category> getCategories(String nameFilter);
    void categorizeTransaction(Transaction transaction, String categoryName);
    void labelGroup(Transactions<? extends Transaction> transactions, String groupName);
    Transactions<CreditTransaction> getCreditTransactions (WhichMonth whichMonth);
}
