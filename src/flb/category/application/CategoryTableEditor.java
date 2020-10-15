package flb.category.application;

import javax.swing.*;
import java.util.*;
import flb.category.*;

public class CategoryTableEditor {
    private final CategoryStorage categoryStorage;
    private final CategoryTable categoryTable;
    private final JTextField nameFilter;

    public CategoryTableEditor(CategoryStorage categoryStorage, CategoryTable categoryTable, JTextField nameFilter){
        this.categoryStorage = categoryStorage;
        this.categoryTable = categoryTable;
        this.nameFilter = nameFilter;
    }

    public void userAddCategory() {
        String categoryToAdd = nameFilter.getText();
        if(!categoryToAdd.equals("") && !categoryStorage.categoryExist(categoryToAdd)) {
            categoryStorage.addCategory(categoryToAdd);
            nameFilter.setText("");
        }
        ArrayList<Category> categories = categoryStorage.getCategories(nameFilter.getText());
        categoryTable.rowChangeRefresh(categories);
    }

    public void userDeleteCategory(JFrame frame) {
        ArrayList<Category> maybeCategory = categoryTable.getSelectedCategory();
        if (!maybeCategory.isEmpty()) {
            String categoryNameToDelete = maybeCategory.get(0).getName();
            int selection = getSelectionFromDialog(categoryNameToDelete, frame);
            if (selection == JOptionPane.YES_OPTION) {
                categoryStorage.deleteCategory(categoryNameToDelete);
            }
        }
        ArrayList<Category> categories = categoryStorage.getCategories(nameFilter.getText());
        categoryTable.rowChangeRefresh(categories);
    }

    protected int getSelectionFromDialog(String categoryNameToDelete, JFrame frame) {
        return JOptionPane.showConfirmDialog(
                frame, "Are you sure you want to delete " + categoryNameToDelete + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
    }

    public void userFiltersCategories(){
        ArrayList<Category> categories = categoryStorage.getCategories(nameFilter.getText());
        categoryTable.rowChangeRefresh(categories);
    }

    public void userClearCategoryGoal() {
        ArrayList<Category> maybeCategory = categoryTable.getSelectedCategory();
        if (!maybeCategory.isEmpty()) {
            String categoryToClear = maybeCategory.get(0).getName();
            categoryStorage.updateAmount(categoryToClear, Float.NaN);
        }
        ArrayList<Category> categories = categoryStorage.getCategories(nameFilter.getText());
        categoryTable.editRefresh(categories);
    }

    public void userEditsExcludes() {
        ArrayList<Category> maybeCategory = categoryTable.getSelectedCategory();
        if (!maybeCategory.isEmpty()) {
            String selectedName = maybeCategory.get(0).getName();
            categoryStorage.toggleExclusion(selectedName);
        }
        ArrayList<Category> categories = categoryStorage.getCategories(nameFilter.getText());
        categoryTable.editRefresh(categories);
    }

    public void userEditsGoalAmount() {
        ArrayList<Category> maybeCategory = categoryTable.getSelectedCategory();
        if (!maybeCategory.isEmpty()) {
            String categoryToUpdate = maybeCategory.get(0).getName();
            float newAmount = maybeCategory.get(0).getDefaultGoal();
            categoryStorage.updateAmount(categoryToUpdate, newAmount);
        }
        ArrayList<Category> categories = categoryStorage.getCategories(nameFilter.getText());
        categoryTable.editRefresh(categories);
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
        ArrayList<Category> categories = categoryStorage.getCategories(nameFilter.getText());
        categoryTable.editRefresh(categories);
    }
}
