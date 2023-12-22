package com.jag2k2.util;

import com.jag2k2.tuples.Transaction;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class TransactionsImpl<T extends Transaction> implements Transactions<T>{

    private final List<T> transactions = new ArrayList<>();

    @Override
    public void add(T transaction) {
        transactions.add(transaction);
    }

    @Override
    public void addAll(Transactions<T> transactions) {
        for (T transaction : transactions)
        {
            this.transactions.add(transaction);
        }
    }

    @Override
    public int size(){
        return transactions.size();
    }

    @Override
    public void clear() {
        transactions.clear();
    }

    @Override
    public T get(int index) {
        return transactions.get(index);
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder("[");
        int count = 0;
        for (T element : transactions){
            output.append(element.toString());
            count++;
            if (count < transactions.size()){
                output.append(", ");
            }
        }
        output.append("]");
        return output.toString();
    }

    @Override
    public boolean equals(Object toCompare) {
        if (this == toCompare) return true;
        if (this.getClass() != toCompare.getClass()) return false;
        TransactionsImpl<T> transactionsToCompare = (TransactionsImpl<T>) toCompare;
        if (this.transactions.size() != transactionsToCompare.transactions.size()) return false;
        int count = 0;
        for (T element : transactions) {
            T elementToCompare = transactionsToCompare.get(count);
            if (!element.equals(elementToCompare)) {
                return false;
            }
            count++;
        }
        return true;
    }

    @Override
    public Iterator<T> iterator() {
        return transactions.iterator();
    }
}
