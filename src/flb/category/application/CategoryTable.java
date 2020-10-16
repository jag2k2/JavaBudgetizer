package flb.category.application;

import flb.category.Category;

import javax.swing.*;
import java.util.*;

public class CategoryTable {
    private final CategoryTableModel tableModel;
    private final JTable table;

    public CategoryTable (JTable table, CategoryTableModel tableModel) {
        this.table = table;
        this.tableModel = tableModel;
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

    public ArrayList<Category> getSelectedCategory() {
        int selectedRow = table.getSelectedRow();
        return tableModel.getCategory(selectedRow);
    }

    public ArrayList<Category> getEditingCategory() {
        ArrayList<Category> maybeCategory = new ArrayList<>();
        int selectedRow = table.getSelectedRow();
        if(table.isEditing()) {
            maybeCategory = tableModel.getCategory(selectedRow);
        }
        return maybeCategory;
    }

    public void refresh(ArrayList<Category> tableContents){
        tableModel.setContents(tableContents);
        tableModel.fireTableDataChanged();
    }

    public void refreshAndKeepSelection(ArrayList<Category> tableContents){
        int selection = table.getSelectedRow();
        if (selection >= 0) {
            tableModel.setContents(tableContents);
            tableModel.fireTableDataChanged();
            table.getSelectionModel().setSelectionInterval(selection, selection);
        }
    }
}
