package flb.datastores;

import flb.tuples.BankingTransaction;
import flb.tuples.CreditTransaction;
import flb.tuples.Transaction;
import flb.tuples.TransactionSummary;
import flb.util.WhichMonth;

import java.util.ArrayList;

public interface TransactionStore {
    void categorizeTransaction(Transaction transaction, String categoryName);
    void addTransactions(ArrayList<Transaction> transactions);
    ArrayList<Transaction> getBankingTransactions (WhichMonth whichMonth);
    ArrayList<Transaction> getCreditTransactions (WhichMonth whichMonth);
    ArrayList<TransactionSummary> getTransactionSummaries (WhichMonth whichMonth);
}
