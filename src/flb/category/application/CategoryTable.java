package flb.category.application;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.*;
import flb.category.*;
import flb.category.application.listeners.*;

public class CategoryTable {
    private final CategoryTableModel tableModel;
    private final JTextField nameFilter;
    private final JTable table;
    private final JScrollPane tableScroller;

    public CategoryTable(ArrayList<Category> categories){
        tableModel = new CategoryTableModel(categories);
        nameFilter = new JTextField();
        table = new JTable(tableModel);
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
        tableScroller = new JScrollPane(table);
        tableScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        tableScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    }

    public void editRefresh(ArrayList<Category> tableContents){
        int[] selections = table.getSelectionModel().getSelectedIndices();
        tableModel.setContents(tableContents);
        tableModel.fireTableDataChanged();
        table.getSelectionModel().setSelectionInterval(selections[0], selections[0]);
    }

    public void rowChangeRefresh(ArrayList<Category> tableContents){
        tableModel.setContents(tableContents);
        tableModel.fireTableDataChanged();
    }

    public String getSelectedRowName() {
        int selectedRow = table.getSelectedRow();
        if(selectedRow < 0){
            return "";
        }
        else {
            return tableModel.getRowName(selectedRow);
        }
    }

    public Float getSelectedDefaultGoal() {
        int selectedRow = table.getSelectedRow();
        return tableModel.getDefaultGoal(selectedRow);
    }

    public boolean getRowSelectedExcludes() {
        int selectedRow = table.getSelectedRow();
        return tableModel.getExcludesAt(selectedRow);
    }

    public void addFilterListener(UserFiltersCategoriesListener filterListener){
        nameFilter.getDocument().addDocumentListener(filterListener);
    }

    public void addExcludeEditorListener(UserEditsExcludesListener editsExcludesListener) {
        table.getDefaultEditor(Boolean.class).addCellEditorListener(editsExcludesListener);
    }

    public void addRenameEditorListener(UserRenamesSelectionListener renameListener) {
        table.addPropertyChangeListener(renameListener);
    }

    public void addGoalAmountEditorListener(UserEditsGoalAmountListener goalEditListener) {
        tableModel.addTableModelListener(goalEditListener);
    }

    public boolean isEditing() {
        return table.isEditing();
    }

    public int getEditingColumn() {
        return table.getEditingColumn();
    }

    public String getFilterText() {
        return nameFilter.getText();
    }

    public void clearFilterText() {
        nameFilter.setText("");
    }

    public JScrollPane getTablePane() {
        return tableScroller;
    }

    public JTextField getNameFilter() {
        return nameFilter;
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
