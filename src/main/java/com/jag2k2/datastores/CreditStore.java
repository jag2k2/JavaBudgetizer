package com.jag2k2.datastores;

import com.jag2k2.tuples.*;
import com.jag2k2.util.WhichMonth;
import com.jag2k2.util.Transactions;
import java.util.ArrayList;

public interface CreditStore extends StoreChangeNotifier {
    ArrayList<Category> getCategories(String nameFilter);
    void categorizeTransaction(Transaction transaction, String categoryName);
    void labelGroup(Transactions<? extends Transaction> transactions, String groupName);
    Transactions<CreditTransaction> getCreditTransactions (WhichMonth whichMonth);
}
