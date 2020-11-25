package flb.util;

import flb.tuples.Transaction;
import java.util.*;

public class Statement<T extends Transaction> implements Transactions<T>{

    private final List<T> transactions;

    public Statement(){
        this.transactions = new ArrayList<>();
    }

    @Override
    public void add(T transaction){
        transactions.add(transaction);
    }

    @Override
    public Iterator<T> iterator() {
        return transactions.iterator();
    }
}
