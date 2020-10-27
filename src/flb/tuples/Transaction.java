package flb.tuples;

import java.util.*;

public class Transaction {
    private final String reference;
    private final Calendar date;
    private final String description;
    private final float amount;
    private final String categoryName;

    public Transaction (String reference, Calendar date, String description, float amount, String categoryName) {
        this.reference = reference;
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.categoryName = categoryName;
    }

    public String getDateString() {
        return date.get(Calendar.YEAR) + "-" +
                (1+date.get(Calendar.MONTH)) + "-" +
                date.get(Calendar.DAY_OF_MONTH);
    }

    public String getDescription() {
        return description;
    }

    public float getAmount() {
        return amount;
    }

    public String getCategory(){
        return categoryName;
    }

    @Override
    public String toString() {
        return (reference + " " +
                date.get(Calendar.YEAR) + "-" + date.get(Calendar.MONTH) + "-" + date.get(Calendar.DAY_OF_MONTH) + " "
                + description + " " + amount + " " + categoryName);
    }

    @Override
    public boolean equals(Object toCompare) {
        if (this == toCompare) return true;
        if (this.getClass() != toCompare.getClass()) return false;
        Transaction TransactionToCompare = (Transaction) toCompare;
        return this.reference.equals(TransactionToCompare.reference) &&
                this.date.equals(TransactionToCompare.date) &&
                this.description.equals(TransactionToCompare.description) &&
                (this.amount == TransactionToCompare.amount) &&
                this.categoryName.equals(TransactionToCompare.categoryName);
    }
}
