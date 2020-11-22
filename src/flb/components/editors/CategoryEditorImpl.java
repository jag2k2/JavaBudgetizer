package flb.components.editors;

import javax.swing.*;
import java.util.*;
import flb.listeners.*;
import flb.components.menus.CategoryEditorMenuImpl;
import flb.datastores.CategoryStore;
import flb.components.editors.tables.*;
import flb.util.*;
import flb.tuples.*;

public class CategoryEditorImpl implements CategoryAdder, CategoryClearer, CategoryDeleter, CategoryExcludeEditor,
        CategoryGoalEditor, CategoryNameEditor, CategoryEditorTester {
    private final CategoryStore categoryStore;
    private final CategoryTable categoryTable;
    private final CategoryTableTester tableAutomator;

    public CategoryEditorImpl(CategoryStore categoryStore){
        this.categoryStore = categoryStore;
        CategoryTableImpl categoryTableImpl = new CategoryTableImpl();
        this.categoryTable = categoryTableImpl;
        this.tableAutomator = categoryTableImpl;
    }

    public void addCategoryEditingListeners(JTextField nameFilter, JFrame frame) {
        categoryTable.addCategoryRenameListener(new UserRenamesCategoryListener(this, nameFilter));
        categoryTable.addGoalEditListener(new UserEditsDefaultGoalListener(this, nameFilter));
        categoryTable.addExcludesEditListener(new UserEditsExcludesListener(this, nameFilter));
        categoryTable.addEditorMenu(new CategoryEditorMenuImpl(this, nameFilter, frame));
    }

    public JScrollPane getPane() {
        return categoryTable.getPane();
    }

    @Override
    public CategoryTableTester getTableTester() {
        return tableAutomator;
    }

    @Override
    public boolean categoryNameAddable(String categoryToAdd) {
        return (!categoryToAdd.equals("") && !categoryStore.categoryExist(categoryToAdd));
    }

    @Override
    public void userAddCategory(String categoryToAdd) {
        if (categoryNameAddable(categoryToAdd)) {
            categoryStore.addCategory(categoryToAdd);
        }
    }

    @Override
    public void userDeleteCategory(int row, JFrame frame) {
        for (Category selectedCategory : categoryTable.getCategory(row)) {
            String categoryNameToDelete = selectedCategory.getName();
            int confirmation = getConfirmationFromDialog(categoryNameToDelete, frame);
            if (confirmation == JOptionPane.YES_OPTION) {
                int transactionCount = categoryStore.getTransactionCountOfCategory(categoryNameToDelete);
                if (transactionCount == 0){
                    categoryStore.deleteCategory(categoryNameToDelete);
                }
                else {
                    notifyUserWhyWontDelete(categoryNameToDelete, transactionCount, frame);
                }
            }
        }
    }

    protected int getConfirmationFromDialog(String categoryNameToDelete, JFrame frame) {
        return JOptionPane.showConfirmDialog(
                frame, "Are you sure you want to delete " + categoryNameToDelete + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
    }

    protected void notifyUserWhyWontDelete(String categoryNameToDelete, int transactionCount, JFrame frame) {
        String message = "Can not delete '$Name'.  There are $count transactions that have been assigned that category.";
        message = message.replace("$Name", categoryNameToDelete);
        message = message.replace("$count", Integer.toString(transactionCount));
        JOptionPane.showMessageDialog(frame, message, "Can Not Delete", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void userClearGoalAmount(int row) {
        for (Category selectedCategory : categoryTable.getCategory(row)) {
            String categoryToClear = selectedCategory.getName();
            categoryStore.updateAmount(categoryToClear, Float.NaN);
        }
    }

    @Override
    public void userEditsSelectedExcludes() {
        for (Category selectedCategory : categoryTable.getSelectedCategory()) {
            String selectedName = selectedCategory.getName();
            categoryStore.toggleExclusion(selectedName);
        }
    }

    @Override
    public void UpdateSelectedGoalAmount() {
        for (Category selectedCategory : categoryTable.getSelectedCategory()) {
            String categoryToUpdate = selectedCategory.getName();
            for (float newAmount : selectedCategory.getDefaultGoal()) {
                categoryStore.updateAmount(categoryToUpdate, newAmount);
            }
        }
    }

    @Override
    public Maybe<Category> getSelectedCategory() {
        return categoryTable.getSelectedCategory();
    }

    @Override
    public void userRenamedCategory(String oldName) {
        for (Category selectedCategory : categoryTable.getSelectedCategory()) {
            String newName = selectedCategory.getName();
            categoryStore.renameCategory(oldName, newName);
        }
    }

    @Override
    public void refreshAndClearSelection(String nameFilter) {
        ArrayList<Category> categories = categoryStore.getCategories(nameFilter);
        categoryTable.displayAndClearSelection(categories);
    }

    @Override
    public void refreshAndKeepSelection(String nameFilter) {
        ArrayList<Category> categories = categoryStore.getCategories(nameFilter);
        categoryTable.displayAndKeepSelection(categories);
    }
}
