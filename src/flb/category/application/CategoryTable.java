package flb.category.application;

import flb.category.Category;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.text.DecimalFormat;
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

    public ArrayList<Category> getRenamedCategory() {
        ArrayList<Category> maybeCategory = new ArrayList<>();
        int selectedRow = table.getSelectedRow();
        if (table.getEditingColumn() == 0) {
            maybeCategory = tableModel.getCategory(selectedRow);
        }
        return maybeCategory;
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

    static class GoalRenderer extends DefaultTableCellRenderer {
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
