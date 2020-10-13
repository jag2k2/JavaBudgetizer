package flb.category.application;

import javax.swing.*;
import java.awt.*;
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
        tableScroller = new JScrollPane(table);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);
        table.getColumnModel().getColumn(0).setMinWidth(120);
        table.getColumnModel().getColumn(1).setMinWidth(80);
        table.getColumnModel().getColumn(1).setMaxWidth(80);
        table.getColumnModel().getColumn(2).setMinWidth(60);
        table.getColumnModel().getColumn(2).setMaxWidth(60);
        //table.setMinimumSize(new Dimension(275, 300));
        //tableScroller.setMinimumSize(new Dimension(275, 300));
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
        table.getModel().addTableModelListener(goalEditListener);
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
}
