package com.jag2k2.databases;

import com.jag2k2.tuples.Category;

import java.util.Arrays;

public class CategoryFactory {
    private static final String[] names = {"Income", "Name2", "Name3", "Test1::sub1", "Test1::sub2"};
    private static final float[] amounts = {1000, 200, 300, Float.NaN, 500};
    private static final boolean[] excludes = {false, true, false, false, true};

    static public String[] getNames(){
        return names;
    }

    static public Category makeCategory(String name){
        int index = Arrays.binarySearch(names, name);
        return new Category(names[index], amounts[index], excludes[index]);
    }

    static public Category makeCategoryWithNoDefaultGoal(String name){
        int index = Arrays.binarySearch(names, name);
        return new Category(names[index], excludes[index]);
    }

    static public Category makeIncludedCategory(String name){
        return new Category(name, false);
    }

    static public Category makeCategoryWithNewName(String name, String newName){
        int index = Arrays.binarySearch(names, name);
        return new Category(newName, amounts[index], excludes[index]);
    }

    static public Category makeCategoryWithNewAmount(String name, float amount){
        int index = Arrays.binarySearch(names, name);
        return new Category(names[index], amount, excludes[index]);
    }

    static public Category makeCategoryWithNewExcluded(String name, boolean excluded){
        int index = Arrays.binarySearch(names, name);
        return new Category(names[index], amounts[index], excluded);
    }
}
