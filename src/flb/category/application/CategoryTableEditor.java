package flb.category.application;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.text.*;
import java.util.*;
import flb.category.*;

public class CategoryTableEditor {
    private final CategoryStorage categoryStorage;
    private final CategoryTableModel tableModel;
    private final JTable table;
    private final JTextField nameFilter;
    private String oldName;

    public CategoryTableEditor(CategoryStorage categoryStorage, JTable table, CategoryTableModel tableModel, JTextField nameFilter){
        this.categoryStorage = categoryStorage;
        this.tableModel = tableModel;
        this.table = table;
        this.nameFilter = nameFilter;
        this.oldName = "";
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);
        GoalRenderer goalRenderer = new GoalRenderer();
        goalRenderer.setHorizontalAlignment(JLabel.RIGHT);
        table.getColumnModel().getColumn(1).setCellRenderer(goalRenderer);
        table.getColumnModel().getColumn(0).setMinWidth(120);
        table.getColumnModel().getColumn(1).setMinWidth(80);
        table.getColumnModel().getColumn(1).setMaxWidth(80);
        table.getColumnModel().getColumn(2).setMinWidth(60);
        table.getColumnModel().getColumn(2).setMaxWidth(60);
    }

    public void userAddCategory() {
        String categoryToAdd = nameFilter.getText();
        if(!categoryToAdd.equals("") && !categoryStorage.categoryExist(categoryToAdd)) {
            categoryStorage.addCategory(categoryToAdd);
            nameFilter.setText("");
            ArrayList<Category> categories = categoryStorage.getCategories(nameFilter.getText());
            rowChangeRefresh(categories);
        }
    }

    public void userDeleteCategory(JFrame frame) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String categoryNameToDelete = tableModel.getCategoryName(selectedRow);
            int selection = getSelectionFromDialog(categoryNameToDelete, frame);
            if (selection == JOptionPane.YES_OPTION) {
                categoryStorage.deleteCategory(categoryNameToDelete);
                ArrayList<Category> categories = categoryStorage.getCategories(nameFilter.getText());
                rowChangeRefresh(categories);
            }
        }
    }

    protected int getSelectionFromDialog(String categoryNameToDelete, JFrame frame) {
        return JOptionPane.showConfirmDialog(
                frame, "Are you sure you want to delete " + categoryNameToDelete + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
    }

    public void userFiltersCategories(){
        String filterText = nameFilter.getText();
        ArrayList<Category> categories = categoryStorage.getCategories(filterText);
        rowChangeRefresh(categories);
    }

    public void userClearCategoryGoal() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String categoryToClear = tableModel.getCategoryName(selectedRow);
            categoryStorage.updateAmount(categoryToClear, Float.NaN);
            ArrayList<Category> categories = categoryStorage.getCategories(nameFilter.getText());
            editRefresh(categories);
        }
    }

    public void userEditsExcludes() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String selectedName = tableModel.getCategoryName(selectedRow);
            categoryStorage.toggleExclusion(selectedName);
            ArrayList<Category> categories = categoryStorage.getCategories(nameFilter.getText());
            editRefresh(categories);
        }
    }

    public void userEditsGoalAmount(int editedColumn) {
        if (editedColumn == 1) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String categoryToUpdate = tableModel.getCategoryName(selectedRow);
                Float newAmount = tableModel.getDefaultGoal(selectedRow);
                categoryStorage.updateAmount(categoryToUpdate, newAmount);
                ArrayList<Category> categories = categoryStorage.getCategories(nameFilter.getText());
                editRefresh(categories);
            }
        }
    }

    public void userRenamesCategory(String eventProperty) {
        if(eventProperty.equals("tableCellEditor")) {
            int selection = table.getSelectedRow();
            if(table.isEditing()){
                oldName = tableModel.getCategoryName(selection);
            }
            else {
                if (table.getEditingColumn() == 0) {
                    String newName = tableModel.getCategoryName(selection);
                    categoryStorage.renameCategory(oldName, newName);
                    ArrayList<Category> categories = categoryStorage.getCategories(nameFilter.getText());
                    editRefresh(categories);
                }
            }
        }
    }

    public void editRefresh(ArrayList<Category> tableContents){
        int selection = table.getSelectedRow();
        if (selection >= 0) {
            tableModel.setContents(tableContents);
            tableModel.fireTableDataChanged();
            table.getSelectionModel().setSelectionInterval(selection, selection);
        }
    }

    public void rowChangeRefresh(ArrayList<Category> tableContents){
        tableModel.setContents(tableContents);
        tableModel.fireTableDataChanged();
    }

    static class GoalRenderer  extends DefaultTableCellRenderer{
        private static final DecimalFormat formatter = new DecimalFormat("#.00");

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value.equals(Float.NaN)) {
                value = "";
            }
            else{
                value = formatter.format(value);
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
}
