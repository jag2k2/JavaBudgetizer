package flb.databases;

import flb.tuples.Category;

public class CategoryFactory {
    private static final String[] names = {"Income", "Name2", "Name3", "Test1::sub1", "Test1::sub2"};
    private static final float[] amounts = {1000, 200, 300, Float.NaN, 500};
    private static final boolean[] excludes = {false, true, false, false, true};

    static public int getNumberOfCategories(){
        return names.length;
    }

    static public boolean nameEquals(int index, String name){
        return names[index].equals(name);
    }

    static public boolean nameContains(int index, String name){
        return names[index].contains(name);
    }

    static public Category makeCategory(int index){
        return new Category(names[index], amounts[index], excludes[index]);
    }

    static public Category makeCategoryWithNoDefaultGoal(int index){
        return new Category(names[index], excludes[index]);
    }

    static public Category makeIncludedCategory(String name){
        return new Category(name, false);
    }

    static public Category makeCategoryWithNewName(int index, String name){
        return new Category(name, amounts[index], excludes[index]);
    }

    static public Category makeCategoryWithNewAmount(int index, float amount){
        return new Category(names[index], amount, excludes[index]);
    }

    static public Category makeCategoryWithNewExcluded(int index, boolean excluded){
        return new Category(names[index], amounts[index], excluded);
    }
}
