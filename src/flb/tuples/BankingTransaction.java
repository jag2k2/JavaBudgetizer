package flb.tuples;

import java.util.*;

public class BankingTransaction extends Transaction{
    private final float balance;

    public BankingTransaction(String reference, Calendar date, String description, float amount, String categoryName, float balance){
        super(reference, date, description, amount, categoryName);
        this.balance = balance;
    }

    public String getUniquifier(){
        return "transactions.id";
    }

    @Override
    public float getBalance() {
        return balance;
    }

    @Override
    public String getTypeString(){
        return "banking";
    }

    @Override
    public String getTerribleTemporaryHackyCondition(){
        return "trans.date = temp.date AND trans.description = temp.description AND trans.amount = temp.amount AND trans.balance = temp.balance";
    }

    @Override
    public String toString() {
        return super.toString() + " | " + balance;
    }

    @Override
    public boolean equals(Object toCompare) {
        return super.equals(toCompare) && (balance == ((BankingTransaction)toCompare).balance);
    }
}
