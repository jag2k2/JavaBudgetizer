package flb.components.editor.category;

import javax.swing.*;
import java.util.*;
import java.awt.*;

import flb.components.editor.*;
import flb.listeners.*;
import flb.components.menus.CategoryEditorMenuImpl;
import flb.datastores.CategoryStore;
import flb.util.*;
import flb.tuples.*;

public class CategoryEditorImpl implements CategoryAdder, CategoryClearer, CategoryDeleter, CategoryExcludeEditor,
        CategoryGoalEditor, CategoryNameEditor, CategoryEditorTester, StoreChangeObserver, ViewChangeObserver {
    private final CategoryStore categoryStore;
    private final CategoryTable categoryTable;
    private final CategoryTableTester tableAutomator;
    private final JTextField nameFilter;
    private final JButton addButton;
    private final JPanel panel;

    public CategoryEditorImpl(CategoryStore categoryStore){
        this.categoryStore = categoryStore;
        CategoryTableImpl categoryTableImpl = new CategoryTableImpl();
        this.categoryTable = categoryTableImpl;
        this.tableAutomator = categoryTableImpl;
        this.addButton = new JButton("Add");
        this.nameFilter = new JTextField();
        this.panel = new JPanel(new BorderLayout());

        JPanel northCategoryPanel = new JPanel();
        northCategoryPanel.setLayout(new BoxLayout(northCategoryPanel, BoxLayout.X_AXIS));
        northCategoryPanel.add(nameFilter);
        northCategoryPanel.add(addButton);

        panel.add(BorderLayout.NORTH, northCategoryPanel);
        panel.add(BorderLayout.CENTER, categoryTable.getPane());

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

    public JPanel getPanel() {
        return panel;
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
    public void userAddCategory() {
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
    public void userDeleteCategory(int row, Component component) {
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
