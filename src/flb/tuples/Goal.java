package flb.tuples;

import flb.util.*;

public class Goal {
    private WhichMonth whichMonth;
    private Category category;
    private float amount;

    public Goal (WhichMonth whichMonth, Category category, float amount){
        this.whichMonth = whichMonth;
        this.category = category;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return whichMonth.toString() + " | " + category.toString() + " | " + amount;
    }

    @Override
    public boolean equals(Object toCompare){
        if (this == toCompare) return true;
        if (this.getClass() != toCompare.getClass()) return false;
        Goal goalToCompare = (Goal) toCompare;
        return this.whichMonth.equals(goalToCompare.whichMonth) &&
                this.category.equals(goalToCompare.category) &&
                this.amount == goalToCompare.amount;
    }

}
