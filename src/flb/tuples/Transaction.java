package flb.tuples;

import flb.util.WhichMonth;
import java.util.*;

abstract public class Transaction {
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

    public String getReference() {
        return reference;
    }

    public WhichMonth getWhichMonth() {
        return new WhichMonth(date.get(Calendar.YEAR), date.get(Calendar.MONTH));
    }

    public String getDateString() {
        return date.get(Calendar.YEAR) + "-" +
                String.format("%02d", (1+date.get(Calendar.MONTH))) + "-" +
                String.format("%02d", date.get(Calendar.DAY_OF_MONTH));
    }

    abstract public String getTypeString();

    abstract public float getBalance();

    abstract public String getPayGroup();

    public String getDescription() {
        return description;
    }

    public String getDescriptionWithEscapes() {
        return description.replace("'", "''");
    }

    public float getAmount() {
        return amount;
    }

    public String getCategoryName(){
        return categoryName;
    }

    @Override
    public String toString() {
        return (reference + " | " + getDateString() + " | " + description + " | " + amount + " | " + categoryName);
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
