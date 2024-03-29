package com.jag2k2.components.editor.category;

import javax.swing.*;
import java.util.*;
import java.awt.*;

import com.jag2k2.components.editor.*;
import com.jag2k2.listeners.*;
import com.jag2k2.components.menus.CategoryEditorMenuImpl;
import com.jag2k2.datastores.CategoryStore;
import com.jag2k2.util.*;
import com.jag2k2.tuples.*;

public class CategoryEditorImpl extends JComponent implements CategoryAdder, CategoryClearer, CategoryDeleter, CategoryExcludeEditor,
        CategoryGoalEditor, CategoryNameEditor, CategoryEditorTester, StoreChangeObserver, ViewChangeObserver {
    private final CategoryStore categoryStore;
    private final CategoryTable categoryTable;
    private final CategoryTableTester tableAutomator;
    private final JTextField nameFilter;
    private final JButton addButton;

    public CategoryEditorImpl(CategoryStore categoryStore){
        this.categoryStore = categoryStore;
        CategoryTableImpl categoryTableImpl = new CategoryTableImpl();
        this.categoryTable = categoryTableImpl;
        this.tableAutomator = categoryTableImpl;
        this.addButton = new JButton("Add");
        this.nameFilter = new JTextField();

        JPanel northCategoryPanel = new JPanel();
        northCategoryPanel.setLayout(new BoxLayout(northCategoryPanel, BoxLayout.X_AXIS));
        northCategoryPanel.add(nameFilter);
        northCategoryPanel.add(addButton);

        this.setLayout(new BorderLayout());
        this.add(northCategoryPanel, BorderLayout.NORTH);
        this.add(categoryTableImpl, BorderLayout.CENTER);

        categoryStore.addStoreChangeObserver(this);
        addCategoryEditingListeners();
    }

    private void addCategoryEditingListeners() {
        categoryTable.addCategoryRenameListener(new UserRenamesCategoryListener(this));
        categoryTable.addGoalEditListener(new UserEditsDefaultGoalListener(this));
        categoryTable.addExcludesEditListener(new UserEditsExcludesListener(this));
        categoryTable.addEditorMenu(new CategoryEditorMenuImpl(this));
        addButton.addActionListener(new UserAddsCategoryListener(this));
        nameFilter.getDocument().addDocumentListener(new UserFiltersCategoriesListener(this));
    }

    @Override
    public CategoryTableTester getTableTester() {
        return tableAutomator;
    }

    @Override
    public void setNameFilter(String nameText) {
        nameFilter.setText(nameText);
        update();
    }

    @Override
    public String getNameFilter() {
        return nameFilter.getText();
    }

    @Override
    public void addCategory() {
        String nameToAdd = nameFilter.getText();
        if (categoryNameAddable(nameToAdd)) {
            categoryStore.addCategory(nameToAdd);
            nameFilter.setText("");
        }
    }

    protected boolean categoryNameAddable(String categoryToAdd) {
        return (!categoryToAdd.equals("") && !categoryStore.categoryExist(categoryToAdd));
    }

    @Override
    public void deleteCategory(int row, Component component) {
        for (Category selectedCategory : categoryTable.getCategory(row)) {
            String categoryNameToDelete = selectedCategory.getName();
            int confirmation = getConfirmationFromDialog(categoryNameToDelete, component);
            if (confirmation == JOptionPane.YES_OPTION) {
                int transactionCount = categoryStore.getTransactionCountOfCategory(categoryNameToDelete);
                if (transactionCount == 0){
                    categoryStore.deleteCategory(categoryNameToDelete);
                }
                else {
                    notifyUserWhyWontDelete(categoryNameToDelete, transactionCount, component);
                }
            }
        }
    }

    protected int getConfirmationFromDialog(String categoryNameToDelete, Component component) {
        return JOptionPane.showConfirmDialog(
                component, "Are you sure you want to delete " + categoryNameToDelete + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
    }

    protected void notifyUserWhyWontDelete(String categoryNameToDelete, int transactionCount, Component component) {
        String message = "Can not delete '$Name'.  There are $count transactions that have been assigned that category.";
        message = message.replace("$Name", categoryNameToDelete);
        message = message.replace("$count", Integer.toString(transactionCount));
        JOptionPane.showMessageDialog(component, message, "Can Not Delete", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void clearGoalAmount(int row) {
        for (Category selectedCategory : categoryTable.getCategory(row)) {
            String categoryToClear = selectedCategory.getName();
            categoryStore.updateAmount(categoryToClear, Float.NaN);
        }
    }

    @Override
    public void editsSelectedExcludes() {
        for (Category selectedCategory : categoryTable.getSelectedCategory()) {
            String selectedName = selectedCategory.getName();
            categoryStore.toggleExclusion(selectedName);
        }
    }

    @Override
    public void updateSelectedGoalAmount() {
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
    public void renamedCategory(String oldName) {
        for (Category selectedCategory : categoryTable.getSelectedCategory()) {
            String newName = selectedCategory.getName();
            categoryStore.renameCategory(oldName, newName);
        }
    }

    @Override
    public void update() {
        ArrayList<Category> categories = categoryStore.getCategories(nameFilter.getText());
        categoryTable.displayAndClearSelection(categories);
    }

    @Override
    public void updateAndKeepSelection() {
        ArrayList<Category> categories = categoryStore.getCategories(nameFilter.getText());
        categoryTable.displayAndKeepSelection(categories);
    }
}
