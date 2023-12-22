package com.jag2k2.databases;

import com.jag2k2.tuples.CreditTransaction;
import com.jag2k2.util.Transactions;
import com.jag2k2.util.TransactionsImpl;

public class CreditListFactory {



    static public Transactions<CreditTransaction> makeGroupedTransactions(String[] refs, String payGroup){
        Transactions<CreditTransaction> transactions = new TransactionsImpl<>();
        for (String ref : CreditFactory.getDefaultRefs()) {
            if (contains(refs, ref)) {
                transactions.add(CreditFactory.makeTransactionWithNewPayGroup(ref, payGroup));
            }
            else {
                transactions.add(CreditFactory.makeDefaultTransaction(ref));
            }
        }
        return transactions;
    }

    static private boolean contains(String[] stringArray, String string){
        for (String stringCheck : stringArray){
            if (stringCheck.equals(string)){
                return true;
            }
        }
        return false;
    }

    static public Transactions<CreditTransaction> makeTransactionsWithCategorizedEntry(String refToChange, String category) {
        Transactions<CreditTransaction> transactions = new TransactionsImpl<>();
        for (String ref : CreditFactory.getDefaultRefs()) {
            if (ref.equals(refToChange)) {
                transactions.add(CreditFactory.makeTransactionWithCategory(ref, category));
            } else {
                transactions.add(CreditFactory.makeDefaultTransaction(ref));
            }
        }
        return transactions;
    }

    static public Transactions<CreditTransaction> makeDefaultTransactions() {
        Transactions<CreditTransaction> transactions = new TransactionsImpl<>();
        for(String ref : CreditFactory.getDefaultRefs()) {
            transactions.add(CreditFactory.makeDefaultTransaction(ref));
        }
        return transactions;
    }

    static public Transactions<CreditTransaction> makeDefaultTransactions(String[] refsToMake) {
        Transactions<CreditTransaction> transactions = new TransactionsImpl<>();
        for(String ref : CreditFactory.getDefaultRefs()) {
            if(contains(refsToMake, ref)){
                transactions.add(CreditFactory.makeDefaultTransaction(ref));
            }
        }
        return transactions;
    }

    static public Transactions<CreditTransaction> makeImportingTransactions() {
        Transactions<CreditTransaction> transactions = new TransactionsImpl<>();
        for(String ref : CreditFactory.getImportRefs()) {
            transactions.add(CreditFactory.makeImportingTransaction(ref));
        }
        return transactions;
    }
}
