package com.jag2k2.tuples;

import com.jag2k2.util.*;

public class TransactionSummary {
    private final WhichMonth whichMonth;
    private final Category category;
    private Maybe<Float> goal;
    private Maybe<Float> transaction_sum;

    public TransactionSummary(WhichMonth whichMonth, Category category) {
        this.whichMonth = whichMonth;
        this.category = category;
        this.goal = new Maybe<>();
        this.transaction_sum = new Maybe<>();
    }

    public void addGoal(float goalAmount){
        if (Float.isNaN(goalAmount)) {
            this.goal = new Maybe<>();
        }
        else {
            this.goal = new Maybe<>(Math.abs(goalAmount));
        }
    }

    public void addSum(float transaction_sum){
        this.transaction_sum = new Maybe<>(Math.abs(transaction_sum));
    }

    public WhichMonth getMonth() {
        return whichMonth;
    }

    public String getMonthSQLString() {
        return whichMonth.toSQLString();
    }

    public String getName() {
        return category.getName();
    }

    public Maybe<Float> getGoalAmount() {
        return goal;
    }

    public float getGoalAmountWithDefault(float defaultGoalAmount) {
        for (float goalAmount : goal) {
            return goalAmount;
        }
        return defaultGoalAmount;
    }

    public Maybe<Float> getTransactionSum() {
        return transaction_sum;
    }

    public float getSumWithDefault(float defaultSum) {
        for (float sum : transaction_sum) {
            return sum;
        }
        return defaultSum;
    }

    public float getCategoryBalance() {
        return getGoalAmountWithDefault(0) - getSumWithDefault(0);
    }

    @Override
    public String toString() {
        return whichMonth + " | " + category + " | " + goal + " | " + transaction_sum;
    }

    @Override
    public boolean equals(Object toCompare) {
        if (this == toCompare) return true;
        if (this.getClass() != toCompare.getClass()) return false;
        TransactionSummary summaryToCompare = (TransactionSummary) toCompare;
        return this.whichMonth.equals(summaryToCompare.whichMonth) &&
                this.category.equals(summaryToCompare.category) &&
                this.goal.equals(summaryToCompare.goal) &&
                this.transaction_sum.equals(summaryToCompare.transaction_sum);
    }
}