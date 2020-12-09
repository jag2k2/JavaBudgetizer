package flb.datastores;

import flb.tuples.BankingTransaction;
import flb.tuples.Category;
import flb.tuples.Transaction;
import flb.util.Transactions;
import flb.util.WhichMonth;
import java.util.ArrayList;

public interface BankingStore extends StoreChangeNotifier {
    ArrayList<Category> getCategories(String nameFilter);
    void categorizeTransaction(Transaction transaction, String categoryName);
    Transactions<BankingTransaction> getBankingTransactions (WhichMonth whichMonth);
}
