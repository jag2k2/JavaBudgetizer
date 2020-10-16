package flb.category.application;

import javax.swing.*;
import java.util.*;
import flb.category.*;

public class CategoryTableEditor {
    private final CategoryStorage categoryStorage;
    private final CategoryTable categoryTable;

    public CategoryTableEditor(CategoryStorage categoryStorage, CategoryTable categoryTable){
        this.categoryStorage = categoryStorage;
        this.categoryTable = categoryTable;
    }

    public boolean categoryNameAddable(String categoryToAdd) {
        return (!categoryToAdd.equals("") && !categoryStorage.categoryExist(categoryToAdd));
    }

    public void userAddCategory(String categoryToAdd) {
        if (categoryNameAddable(categoryToAdd)) {
            categoryStorage.addCategory(categoryToAdd);
        }
    }

    public void userDeleteSelectedCategory(JFrame frame) {
        for (Category selectedCategory : categoryTable.getSelectedCategory()) {
            String categoryNameToDelete = selectedCategory.getName();
            int selection = getSelectionFromDialog(categoryNameToDelete, frame);
            if (selection == JOptionPane.YES_OPTION) {
                categoryStorage.deleteCategory(categoryNameToDelete);
            }
        }
    }

    protected int getSelectionFromDialog(String categoryNameToDelete, JFrame frame) {
        return JOptionPane.showConfirmDialog(
                frame, "Are you sure you want to delete " + categoryNameToDelete + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
    }

    public void userClearSelectedCategoryGoal() {
        for (Category selectedCategory : categoryTable.getSelectedCategory()) {
            String categoryToClear = selectedCategory.getName();
            categoryStorage.updateAmount(categoryToClear, Float.NaN);
        }
    }

    public void userEditsSelectedExcludes() {
        for (Category selectedCategory : categoryTable.getSelectedCategory()) {
            String selectedName = selectedCategory.getName();
            categoryStorage.toggleExclusion(selectedName);
        }
    }

    public void userEditsSelectedGoalAmount() {
        for (Category selectedCategory : categoryTable.getSelectedCategory()) {
            String categoryToUpdate = selectedCategory.getName();
            float newAmount = selectedCategory.getDefaultGoal();
            categoryStorage.updateAmount(categoryToUpdate, newAmount);
        }
    }

    public ArrayList<Category> getEditingCategory() {
        return categoryTable.getEditingCategory();
    }

    public void userRenamedCategory(String oldName) {
        for (Category selectedCategory : categoryTable.getSelectedCategory()) {
            String newName = selectedCategory.getName();
            categoryStorage.renameCategory(oldName, newName);
        }
    }

    public void refresh(String nameFilter) {
        ArrayList<Category> categories = categoryStorage.getCategories(nameFilter);
        categoryTable.refresh(categories);
    }

    public void refreshAndKeepSelection(String nameFilter) {
        ArrayList<Category> categories = categoryStorage.getCategories(nameFilter);
        categoryTable.refreshAndKeepSelection(categories);
    }
}
