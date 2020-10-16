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
        ArrayList<Category> maybeCategory = categoryTable.getSelectedCategory();
        if (!maybeCategory.isEmpty()) {
            String categoryNameToDelete = maybeCategory.get(0).getName();
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
        ArrayList<Category> maybeCategory = categoryTable.getSelectedCategory();
        if (!maybeCategory.isEmpty()) {
            String categoryToClear = maybeCategory.get(0).getName();
            categoryStorage.updateAmount(categoryToClear, Float.NaN);
        }
    }

    public void userEditsSelectedExcludes() {
        ArrayList<Category> maybeCategory = categoryTable.getSelectedCategory();
        if (!maybeCategory.isEmpty()) {
            String selectedName = maybeCategory.get(0).getName();
            categoryStorage.toggleExclusion(selectedName);
        }
    }

    public void userEditsSelectedGoalAmount() {
        ArrayList<Category> maybeCategory = categoryTable.getSelectedCategory();
        if (!maybeCategory.isEmpty()) {
            String categoryToUpdate = maybeCategory.get(0).getName();
            float newAmount = maybeCategory.get(0).getDefaultGoal();
            categoryStorage.updateAmount(categoryToUpdate, newAmount);
        }
    }

    public ArrayList<Category> getEditingCategory() {
        return categoryTable.getEditingCategory();
    }

    public void userRenamedCategory(String oldName) {
        ArrayList<Category> maybeCategory = categoryTable.getSelectedCategory();
        if(!maybeCategory.isEmpty()) {
            String newName = maybeCategory.get(0).getName();
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
