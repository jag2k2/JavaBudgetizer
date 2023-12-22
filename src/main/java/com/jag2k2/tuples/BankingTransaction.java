package com.jag2k2.tuples;

import java.util.*;

public class BankingTransaction extends Transaction{
    private final float balance;

    public BankingTransaction(String reference, Calendar date, String description, float amount, String categoryName, float balance){
        super(reference, date, description, amount, categoryName);
        this.balance = balance;
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
    public String toString() {
        return super.toString() + " | " + balance;
    }

    @Override
    public boolean equals(Object toCompare) {
        return super.equals(toCompare) && (balance == ((BankingTransaction)toCompare).balance);
    }
}
