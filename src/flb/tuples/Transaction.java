package flb.tuples;

import java.time.*;

public class Transaction {
    private final String reference;
    private final LocalDate date;
    private final String description;
    private final float amount;
    private final String categoryName;

    public Transaction (String reference, LocalDate date, String description, float amount, String categoryName) {
        this.reference = reference;
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.categoryName = categoryName;
    }

    public LocalDate getDate() {
        return date;
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
        return (reference + " " + date + " " + description + " " + amount + " " + categoryName);
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
