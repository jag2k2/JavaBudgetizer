package flb.components.editors;

import javax.swing.*;
import java.util.*;

import flb.components.editors.tables.listeners.UserEditsExcludesListener;
import flb.components.editors.tables.listeners.UserEditsGoalAmountListener;
import flb.components.editors.tables.listeners.UserRenamesSelectionListener;
import flb.database.CategoryStore;
import flb.components.editors.tables.CategoryTable;
import flb.components.editors.tables.CategoryTableTester;
import flb.components.editors.tables.CategoryTableImpl;
import flb.util.*;
import flb.tuples.*;

public class CategoryEditorImpl implements CategoryAdder, CategoryClearer, CategoryDeleter, CategoryExcludeEditor,
        CategoryAmountEditor, CategoryNameEditor, CategoryEditorAutomator {
    private final CategoryStore categoryStore;
    private final CategoryTable categoryTable;
    private final CategoryTableTester tableAutomator;

    public CategoryEditorImpl(CategoryStore categoryStore){
        this.categoryStore = categoryStore;
        CategoryTableImpl categoryTableImpl = new CategoryTableImpl();
        this.categoryTable = categoryTableImpl;
        this.tableAutomator = categoryTableImpl;
    }

    public void addCategoryEditingListeners(JTextField nameFilter) {
        categoryTable.addCategoryRenameListener(new UserRenamesSelectionListener(this, nameFilter));
        categoryTable.addGoalEditListener(new UserEditsGoalAmountListener(this, nameFilter));
        categoryTable.addExcludesEditListener(new UserEditsExcludesListener(this, nameFilter));
    }

    public JScrollPane getPane() {
        return categoryTable.getPane();
    }

    public CategoryTableTester getTableAutomator() {
        return tableAutomator;
    }

    public boolean categoryNameAddable(String categoryToAdd) {
        return (!categoryToAdd.equals("") && !categoryStore.categoryExist(categoryToAdd));
    }

    public void userAddCategory(String categoryToAdd) {
        if (categoryNameAddable(categoryToAdd)) {
            categoryStore.addCategory(categoryToAdd);
        }
    }

    public void userDeleteSelectedCategory(JFrame frame) {
        for (Category selectedCategory : categoryTable.getSelectedCategory()) {
            String categoryNameToDelete = selectedCategory.getName();
            int selection = getSelectionFromDialog(categoryNameToDelete, frame);
            if (selection == JOptionPane.YES_OPTION) {
                categoryStore.deleteCategory(categoryNameToDelete);
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
            categoryStore.updateAmount(categoryToClear, Float.NaN);
        }
    }

    public void userEditsSelectedExcludes() {
        for (Category selectedCategory : categoryTable.getSelectedCategory()) {
            String selectedName = selectedCategory.getName();
            categoryStore.toggleExclusion(selectedName);
        }
    }

    public void userEditsSelectedGoalAmount() {
        for (Category selectedCategory : categoryTable.getSelectedCategory()) {
            String categoryToUpdate = selectedCategory.getName();
            float newAmount = selectedCategory.getDefaultGoal();
            categoryStore.updateAmount(categoryToUpdate, newAmount);
        }
    }

    public Maybe<Category> getSelectedCategory() {
        return categoryTable.getSelectedCategory();
    }

    public void userRenamedCategory(String oldName) {
        for (Category selectedCategory : categoryTable.getSelectedCategory()) {
            String newName = selectedCategory.getName();
            categoryStore.renameCategory(oldName, newName);
        }
    }

    public void refreshAndClearSelection(String nameFilter) {
        ArrayList<Category> categories = categoryStore.getCategories(nameFilter);
        categoryTable.displayAndClearSelection(categories);
    }

    public void refreshAndKeepSelection(String nameFilter) {
        ArrayList<Category> categories = categoryStore.getCategories(nameFilter);
        categoryTable.displayAndKeepSelection(categories);
    }
}
