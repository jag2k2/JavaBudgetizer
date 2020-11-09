package flb.components.editors;

import javax.swing.*;
import java.util.*;
import flb.listeners.*;
import flb.components.menus.CategoryEditorMenuImpl;
import flb.components.monthselector.MonthSelectorImpl;
import flb.datastores.CategoryStore;
import flb.components.editors.tables.*;
import flb.util.*;
import flb.tuples.*;

public class CategoryEditorImpl implements CategoryAdder, CategoryClearer, CategoryDeleter, CategoryExcludeEditor,
        CategoryAmountEditor, CategoryNameEditor, CategoryEditorAutomator {
    private final CategoryStore categoryStore;
    private final CategoryTable categoryTable;
    private final CategoryTableTester tableAutomator;
    private ArrayList<StoreChangeListener> storeChangeListeners;

    public CategoryEditorImpl(CategoryStore categoryStore){
        this.categoryStore = categoryStore;
        CategoryTableImpl categoryTableImpl = new CategoryTableImpl();
        this.categoryTable = categoryTableImpl;
        this.tableAutomator = categoryTableImpl;
        this.storeChangeListeners = new ArrayList<>();
    }

    public void addCategoryEditingListeners(JTextField nameFilter, JFrame frame, MonthSelectorImpl monthSelector) {
        categoryTable.addCategoryRenameListener(new UserRenamesSelectionListener(this, nameFilter, monthSelector));
        categoryTable.addGoalEditListener(new UserEditsGoalAmountListener(this, nameFilter));
        categoryTable.addExcludesEditListener(new UserEditsExcludesListener(this, nameFilter));
        categoryTable.addEditorMenu(new CategoryEditorMenuImpl(this, nameFilter, frame));
    }

    public void addStoreChangeListener(StoreChangeListener storeChangeListener) {
        storeChangeListeners.add(storeChangeListener);
    }

    public void notifyStoreChange(WhichMonth selectedDate) {
        for (StoreChangeListener storeChangeListener : storeChangeListeners) {
            storeChangeListener.updateAndKeepSelection(selectedDate);
        }
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

    public void userClearGoalAmount(int row) {
        for (Category selectedCategory : categoryTable.getCategory(row)) {
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

    public void userRenamedCategory(String oldName, WhichMonth selectedMonth) {
        for (Category selectedCategory : categoryTable.getSelectedCategory()) {
            String newName = selectedCategory.getName();
            categoryStore.renameCategory(oldName, newName);
        }
        notifyStoreChange(selectedMonth);
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
