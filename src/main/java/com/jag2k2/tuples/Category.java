package com.jag2k2.tuples;

import com.jag2k2.util.*;

public class Category {

    private String name;
    private Boolean exclude;
    private Maybe<Float> default_goal;

    public Category(String name, Boolean exclude) {
        this.name = name;
        this.default_goal = new Maybe<>();
        this.exclude = exclude;
    }

    public Category(String name, float default_goal, Boolean exclude){
        this.name = name;
        if (Float.isNaN(default_goal)){
            this.default_goal = new Maybe<>();
        }
        else{
            this.default_goal = new Maybe<>(default_goal);
        }
        this.exclude = exclude;
    }

    public String getName() {
        return name;
    }

    public Maybe<Float> getDefaultGoal() {
        return default_goal;
    }

    public float getDefaultGoalDisplay() {
        for (float goalAmount : default_goal) {
            return goalAmount;
        }
        return Float.NaN;
    }

    public boolean getExclude() { return exclude; }

    public void rename(String newName) {
        this.name = newName;
    }

    public void setDefaultGoal(Float defaultGoal) {
        this.default_goal = new Maybe<>(Math.abs(defaultGoal));
    }

    public void toggleExcludes(){
        this.exclude = !exclude;
    }

    @Override
    public String toString() {
        return (name + " | " + default_goal + " | " + exclude);
    }

    @Override
    public boolean equals(Object toCompare) {
        if (this == toCompare) return true;
        if (this.getClass() != toCompare.getClass()) return false;
        Category categoryToCompare = (Category) toCompare;
        return this.name.equals(categoryToCompare.name) &&
                this.default_goal.equals(categoryToCompare.default_goal) &&
                this.exclude.equals(categoryToCompare.exclude);
    }
}
