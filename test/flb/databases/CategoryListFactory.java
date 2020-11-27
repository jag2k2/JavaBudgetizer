package flb.databases;

import flb.tuples.Category;
import java.util.ArrayList;

public class CategoryListFactory {

    static public ArrayList<Category> makeDefaultCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        for (String name : CategoryFactory.getNames()){
            categories.add(CategoryFactory.makeCategory(name));
        }
        return categories;
    }

    static public ArrayList<Category> makeFilteredDefaultCategories(String nameFilter){
        ArrayList<Category> categories = new ArrayList<>();
        for (String name : CategoryFactory.getNames()){
            if(name.contains(nameFilter)){
                categories.add(CategoryFactory.makeCategory(name));
            }
        }
        return categories;
    }

    static public ArrayList<Category> makeDefaultCategoriesWithOneRenamed(String oldName, String newName){
        ArrayList<Category> categories = new ArrayList<>();
        for (String name : CategoryFactory.getNames()){
            if (name.equals(oldName)){
                categories.add(CategoryFactory.makeCategoryWithNewName(name, newName));
            }
            else {
                categories.add(CategoryFactory.makeCategory(name));
            }
        }
        return categories;
    }

    static public ArrayList<Category> makeDefaultCategoriesWithOneCleared(String nameToClear){
        ArrayList<Category> categories = new ArrayList<>();
        for (String name : CategoryFactory.getNames()){
            if (name.equals(nameToClear)){
                categories.add(CategoryFactory.makeCategoryWithNoDefaultGoal(name));
            }
            else {
                categories.add(CategoryFactory.makeCategory(name));
            }
        }
        return categories;
    }

    static public ArrayList<Category> makeDefaultCategoriesWithOneAmountChanged(String nameToChange, float amount){
        ArrayList<Category> categories = new ArrayList<>();
        for (String name : CategoryFactory.getNames()){
            if (name.equals(nameToChange)){
                categories.add(CategoryFactory.makeCategoryWithNewAmount(name, amount));
            }
            else {
                categories.add(CategoryFactory.makeCategory(name));
            }
        }
        return categories;
    }

    static public ArrayList<Category> makeDefaultCategoriesWithOneExcludesChanged(String nameToChange, boolean excluded){
        ArrayList<Category> categories = new ArrayList<>();
        for (String name : CategoryFactory.getNames()){
            if (name.equals(nameToChange)){
                categories.add(CategoryFactory.makeCategoryWithNewExcluded(name, excluded));
            }
            else {
                categories.add(CategoryFactory.makeCategory(name));
            }
        }
        return categories;
    }
}
