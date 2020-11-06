package flb.tuples;

public class TransactionSummary {
    private String name;
    private float goal_amount;
    private float transaction_sum;

    public TransactionSummary(String name, float goal_amount, float transaction_sum) {
        this.name = name;
        this.goal_amount = goal_amount;
        this.transaction_sum = transaction_sum;
    }

    public String getName() {
        return name;
    }

    public float getGoalAmount() {
        return goal_amount;
    }

    public float getTransactionSum() {
        return transaction_sum;
    }

    @Override
    public String toString() {
        return name + " | " + goal_amount + " | " + transaction_sum;
    }

    @Override
    public boolean equals(Object toCompare) {
        if (this == toCompare) return true;
        if (this.getClass() != toCompare.getClass()) return false;
        TransactionSummary summaryToCompare = (TransactionSummary) toCompare;
        return this.name.equals(summaryToCompare.name) &&
                Float.compare(this.goal_amount, summaryToCompare.goal_amount) == 0 &&
                Float.compare(this.transaction_sum, summaryToCompare.transaction_sum) == 0;
    }
}