package com.jag2k2.components.editor.category;

import com.jag2k2.components.editor.SimpleDollarRenderer;
import com.jag2k2.listeners.*;
import com.jag2k2.components.menus.MenuDisplayer;
import com.jag2k2.tuples.*;
import com.jag2k2.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.beans.*;
import java.util.*;
import java.util.List;

public class CategoryTableImpl extends JComponent implements CategoryTable, CategoryTableTester {
    private final CategoryTableModel tableModel;
    private final JTable table;
    private final JScrollPane scrollPane;

    public CategoryTableImpl() {
        CategoryTableModelImpl categoryTableModel = new CategoryTableModelImpl();
        this.tableModel = categoryTableModel;
        this.table = new JTable(categoryTableModel);
        this.scrollPane = new JScrollPane(table);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);
        table.setRowSelectionAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);
        TableCellRenderer cellRenderer = new SimpleDollarRenderer(table.getDefaultRenderer(Float.class));
        table.getColumnModel().getColumn(1).setCellRenderer(cellRenderer);
        table.getColumnModel().getColumn(0).setMinWidth(220);
        table.getColumnModel().getColumn(0).setMaxWidth(220);
        table.getColumnModel().getColumn(1).setMinWidth(80);
        table.getColumnModel().getColumn(1).setMaxWidth(80);
        table.getColumnModel().getColumn(2).setMinWidth(60);
        table.getColumnModel().getColumn(2).setMaxWidth(60);
        table.setPreferredScrollableViewportSize(new Dimension(360,-1));

        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        this.setLayout(new BorderLayout());
        this.add(scrollPane);
    }

    @Override
    public Maybe<Category> getSelectedCategory() {
        int selectedRow = table.getSelectedRow();
        return tableModel.getCategory(selectedRow);
    }

    @Override
    public Maybe<Category> getCategory(int row) {
        return tableModel.getCategory(row);
    }

    @Override
    public void addEditorMenu (MenuDisplayer menuDisplayer) {
        table.add(menuDisplayer.getPopup());
        List<Integer> validGroupingColumns = new ArrayList<>(Arrays.asList(0,1,2));
        table.addMouseListener(new UserRightClicksTableListener(menuDisplayer, validGroupingColumns));
    }

    @Override
    public void displayAndClearSelection(ArrayList<Category> tableContents){
        tableModel.updateCategories(tableContents);
        table.clearSelection();
    }

    @Override
    public void displayAndKeepSelection(ArrayList<Category> tableContents){
        int selectedRow = table.getSelectedRow();
        tableModel.updateCategories(tableContents);
        table.getSelectionModel().setSelectionInterval(selectedRow, selectedRow);
    }

    @Override
    public void addCategoryRenameListener(PropertyChangeListener propertyChangeListener) {
        table.addPropertyChangeListener(propertyChangeListener);
    }

    @Override
    public void addGoalEditListener(TableModelListener tableModelListener) {
        tableModel.addTableModelListener(tableModelListener);
    }

    @Override
    public void addExcludesEditListener(CellEditorListener cellEditorListener) {
        table.getDefaultEditor(Boolean.class).addCellEditorListener(cellEditorListener);
    }

    @Override
    public void setSelectedRow(int row) {
        table.getSelectionModel().setSelectionInterval(row,row);
    }

    @Override
    public int getSelectedRow() {
        return table.getSelectedRow();
    }

    @Override
    public ArrayList<Category> getContents() {
        return tableModel.getCategories();
    }

    @Override
    public void editCellAt(int row, int col) {
        table.editCellAt(row, col);
    }

    @Override
    public void toggleSelectedExcludes() {
        TableCellEditor cellEditor =  table.getDefaultEditor(Boolean.class);
        JCheckBox editorComponent =  (JCheckBox) cellEditor.getTableCellEditorComponent
                (table, true, false, 0, 2);
        for(Category category : getSelectedCategory()){
            editorComponent.setSelected(!category.getExclude());
        }
        cellEditor.stopCellEditing();
    }

    @Override
    public void setEditorGoal(float newGoal) {
        TableCellEditor cellEditor = table.getDefaultEditor(Float.class);
        JTextField editorComponent = (JTextField) cellEditor.getTableCellEditorComponent
                (table, "", false, 0, 1);
        editorComponent.setText(Float.toString(newGoal));
        cellEditor.stopCellEditing();
    }

    @Override
    public void setEditorName(String newName) {
        TableCellEditor cellEditor = table.getDefaultEditor(String.class);
        JTextField editorComponent = (JTextField) cellEditor.getTableCellEditorComponent
                (table, "", false, 0, 0);
        editorComponent.setText(newName);
        cellEditor.stopCellEditing();
    }
}
