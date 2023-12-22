package com.jag2k2.databases;

import com.jag2k2.tuples.BankingTransaction;
import com.jag2k2.util.Transactions;
import com.jag2k2.util.TransactionsImpl;

public class DebitListFactory {

    static public Transactions<BankingTransaction> makeDefaultTransactions() {
        Transactions<BankingTransaction> transactions = new TransactionsImpl<>();
        for(String ref : DebitFactory.getDefaultRefs()) {
            transactions.add(DebitFactory.makeDefaultTransaction(ref));
        }
        return transactions;
    }

    static public Transactions<BankingTransaction> makeImportingTransactions() {
        Transactions<BankingTransaction> transactions = new TransactionsImpl<>();
        for(String ref : DebitFactory.getImportRefs()) {
            transactions.add(DebitFactory.makeImportingTransaction(ref));
        }
        return transactions;
    }

    static public Transactions<BankingTransaction> makeTransactionsWithCategorizedEntry(String refToChange, String category){
        Transactions<BankingTransaction> transactions = new TransactionsImpl<>();
        for(String ref : DebitFactory.getDefaultRefs()) {
            if (ref.equals(refToChange)){
                transactions.add(DebitFactory.makeTransactionWithCategory(ref, category));
            }
            else {
                transactions.add(DebitFactory.makeDefaultTransaction(ref));
            }
        }
        return transactions;
    }
}
