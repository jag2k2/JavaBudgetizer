package flb.tables;

import javax.swing.*;
import java.util.*;

import flb.database.CategoryStoreEditor;
import flb.util.*;
import flb.tuples.*;
import flb.application.category.listeners.*;

public class CategoryTableEditorImp implements CategoryAdder, CategoryClearer, CategoryDeleter, CategoryExcludeEditor,
                                            CategoryAmountEditor, CategoryNameEditor {
    private final CategoryStoreEditor categoryStoreEditor;
    private final CategoryTable categoryTable;

    public CategoryTableEditorImp(CategoryStoreEditor categoryStoreEditor, CategoryTable categoryTable){
        this.categoryStoreEditor = categoryStoreEditor;
        this.categoryTable = categoryTable;
    }

    public boolean categoryNameAddable(String categoryToAdd) {
        return (!categoryToAdd.equals("") && !categoryStoreEditor.categoryExist(categoryToAdd));
    }

    public void userAddCategory(String categoryToAdd) {
        if (categoryNameAddable(categoryToAdd)) {
            categoryStoreEditor.addCategory(categoryToAdd);
        }
    }

    public void userDeleteSelectedCategory(JFrame frame) {
        for (Category selectedCategory : categoryTable.getSelectedCategory()) {
            String categoryNameToDelete = selectedCategory.getName();
            int selection = getSelectionFromDialog(categoryNameToDelete, frame);
            if (selection == JOptionPane.YES_OPTION) {
                categoryStoreEditor.deleteCategory(categoryNameToDelete);
            }
        }
    }

    protected int getSelectionFromDialog(String categoryNameToDelete, JFrame frame) {
        return JOptionPane.showConfirmDialog(
                frame, "Are you sure you want to delete " + categoryNameToDelete + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
    }

    public void userClearSelectedGoalAmount() {
        for (Category selectedCategory : categoryTable.getSelectedCategory()) {
            String categoryToClear = selectedCategory.getName();
            categoryStoreEditor.updateAmount(categoryToClear, Float.NaN);
        }
    }

    public void userEditsSelectedExcludes() {
        for (Category selectedCategory : categoryTable.getSelectedCategory()) {
            String selectedName = selectedCategory.getName();
            categoryStoreEditor.toggleExclusion(selectedName);
        }
    }

    public void userEditsSelectedGoalAmount() {
        for (Category selectedCategory : categoryTable.getSelectedCategory()) {
            String categoryToUpdate = selectedCategory.getName();
            float newAmount = selectedCategory.getDefaultGoal();
            categoryStoreEditor.updateAmount(categoryToUpdate, newAmount);
        }
    }

    public Maybe<Category> getSelectedCategory() {
        return categoryTable.getSelectedCategory();
    }

    public void userRenamedCategory(String oldName) {
        for (Category selectedCategory : categoryTable.getSelectedCategory()) {
            String newName = selectedCategory.getName();
            categoryStoreEditor.renameCategory(oldName, newName);
        }
    }

    public void refreshAndClearSelection(String nameFilter) {
        ArrayList<Category> categories = categoryStoreEditor.getCategories(nameFilter);
        categoryTable.displayAndClearSelection(categories);
    }

    public void refreshAndKeepSelection(String nameFilter) {
        ArrayList<Category> categories = categoryStoreEditor.getCategories(nameFilter);
        categoryTable.displayAndKeepSelection(categories);
    }
}
