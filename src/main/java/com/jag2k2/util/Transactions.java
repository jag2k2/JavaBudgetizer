package com.jag2k2.util;


import com.jag2k2.tuples.Transaction;

public interface Transactions<T extends Transaction> extends Iterable<T>{
    void add(T transaction);
    void addAll(Transactions<T> transactions);
    int size();
    void clear();
    T get(int index);
}
