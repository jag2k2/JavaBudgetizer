package flb.tables.category;

import flb.tables.goal.GoalRenderer;
import flb.tuples.Category;
import flb.util.*;
import javax.swing.*;
import java.util.*;

public class CategoryTableImp implements CategoryTable{
    private final CategoryTableModel tableModel;
    private final JTable table;

    public CategoryTableImp(JTable table, CategoryTableModel tableModel) {
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

    public Maybe<Category> getSelectedCategory() {
        int selectedRow = table.getSelectedRow();
        return tableModel.getCategory(selectedRow);
    }

    public void displayAndClearSelection(ArrayList<Category> tableContents){
        tableModel.updateCategories(tableContents);
    }

    public void displayAndKeepSelection(ArrayList<Category> tableContents){
        int selection = table.getSelectedRow();
        tableModel.updateCategories(tableContents);
        table.getSelectionModel().setSelectionInterval(selection, selection);
    }
}
