package flb.util;


import flb.tuples.Transaction;

public interface Transactions<T extends Transaction> extends Iterable<T>{
    void add(T transaction);
    void addAll(Transactions<T> transactions);
    int size();
    void clear();
    T get(int index);
}
