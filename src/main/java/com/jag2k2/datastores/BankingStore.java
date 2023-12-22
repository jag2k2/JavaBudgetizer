package com.jag2k2.datastores;

import com.jag2k2.tuples.BankingTransaction;
import com.jag2k2.tuples.Category;
import com.jag2k2.tuples.Transaction;
import com.jag2k2.util.Transactions;
import com.jag2k2.util.WhichMonth;
import java.util.ArrayList;

public interface BankingStore extends StoreChangeNotifier {
    ArrayList<Category> getCategories(String nameFilter);
    void categorizeTransaction(Transaction transaction, String categoryName);
    Transactions<BankingTransaction> getBankingTransactions (WhichMonth whichMonth);
}
