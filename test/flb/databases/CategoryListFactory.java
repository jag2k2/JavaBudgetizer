package flb.databases;

import flb.tuples.Category;
import java.util.ArrayList;

public class CategoryListFactory {

    static public ArrayList<Category> makeDefaultCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        for (int i = 0; i < CategoryFactory.getNumberOfCategories(); i++){
            categories.add(CategoryFactory.makeCategory(i));
        }
        return categories;
    }

    static public ArrayList<Category> makeDefaultCategoriesWithOneRenamed(String name, String newName){
        ArrayList<Category> categories = new ArrayList<>();
        for (int i = 0; i < CategoryFactory.getNumberOfCategories(); i++){
            if (CategoryFactory.nameEquals(i, name)){
                categories.add(CategoryFactory.makeCategoryWithNewName(i, newName));
            }
            else {
                categories.add(CategoryFactory.makeCategory(i));
            }
        }
        return categories;
    }

    static public ArrayList<Category> makeFilteredDefaultCategories(String nameFilter){
        ArrayList<Category> categories = new ArrayList<>();
        for (int i = 0; i < CategoryFactory.getNumberOfCategories(); i++){
            if(CategoryFactory.nameContains(i, nameFilter)){
                categories.add(CategoryFactory.makeCategory(i));
            }
        }
        return categories;
    }

    static public ArrayList<Category> makeDefaultCategoriesWithOneCleared(String name){
        ArrayList<Category> categories = new ArrayList<>();
        for (int i = 0; i < CategoryFactory.getNumberOfCategories(); i++){
            if (CategoryFactory.nameEquals(i, name)){
                categories.add(CategoryFactory.makeCategoryWithNoDefaultGoal(i));
            }
            else {
                categories.add(CategoryFactory.makeCategory(i));
            }
        }
        return categories;
    }

    static public ArrayList<Category> makeDefaultCategoriesWithOneAmountChanged(String name, float amount){
        ArrayList<Category> categories = new ArrayList<>();
        for (int i = 0; i < CategoryFactory.getNumberOfCategories(); i++){
            if (CategoryFactory.nameEquals(i, name)){
                categories.add(CategoryFactory.makeCategoryWithNewAmount(i, amount));
            }
            else {
                categories.add(CategoryFactory.makeCategory(i));
            }
        }
        return categories;
    }

    static public ArrayList<Category> makeDefaultCategoriesWithOneExcludesChanged(String name, boolean excluded){
        ArrayList<Category> categories = new ArrayList<>();
        for (int i = 0; i < CategoryFactory.getNumberOfCategories(); i++){
            if (CategoryFactory.nameEquals(i, name)){
                categories.add(CategoryFactory.makeCategoryWithNewExcluded(i, excluded));
            }
            else {
                categories.add(CategoryFactory.makeCategory(i));
            }
        }
        return categories;
    }
}
