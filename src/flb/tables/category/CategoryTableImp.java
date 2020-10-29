package flb.tables.category;

import flb.tables.goal.GoalRenderer;
import flb.tuples.Category;
import flb.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.beans.*;
import java.util.*;

public class CategoryTableImp extends Component implements CategoryTable {
    private final CategoryTableModelImpl tableModel;
    private final JTable table;
    private final JScrollPane scrollPane;

    public CategoryTableImp() {
        this.tableModel = new CategoryTableModelImpl();

        this.table = new JTable(tableModel);
        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.table.setFillsViewportHeight(true);
        GoalRenderer goalRenderer = new GoalRenderer();
        goalRenderer.setHorizontalAlignment(JLabel.RIGHT);
        this.table.getColumnModel().getColumn(1).setCellRenderer(goalRenderer);
        this.table.getColumnModel().getColumn(0).setMinWidth(120);
        this.table.getColumnModel().getColumn(1).setMinWidth(80);
        this.table.getColumnModel().getColumn(1).setMaxWidth(80);
        this.table.getColumnModel().getColumn(2).setMinWidth(60);
        this.table.getColumnModel().getColumn(2).setMaxWidth(60);

        this.scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    }

    public JScrollPane getPane() {
        return scrollPane;
    }

    public Maybe<Category> getSelectedCategory() {
        int selectedRow = table.getSelectedRow();
        return tableModel.getCategory(selectedRow);
    }

    public void displayAndClearSelection(ArrayList<Category> tableContents){
        tableModel.updateCategories(tableContents);
        table.clearSelection();
    }

    public void displayAndKeepSelection(ArrayList<Category> tableContents){
        int selection = table.getSelectedRow();
        tableModel.updateCategories(tableContents);
        table.getSelectionModel().setSelectionInterval(selection, selection);
    }

    public void addCategoryRenameListener(PropertyChangeListener propertyChangeListener) {
        table.addPropertyChangeListener(propertyChangeListener);
    }

    public void addGoalEditListener(TableModelListener tableModelListener) {
        tableModel.addTableModelListener(tableModelListener);
    }

    public void addExcludesEditListener(CellEditorListener cellEditorListener) {
        table.getDefaultEditor(Boolean.class).addCellEditorListener(cellEditorListener);
    }

    public void setSelectedRow(int row) {
        table.getSelectionModel().setSelectionInterval(row,row);
    }

    public int getSelectedRow() {
        return table.getSelectedRow();
    }

    public ArrayList<Category> getContents() {
        return tableModel.getContents();
    }

    public void editCellAt(int row, int col) {
        table.editCellAt(row, col);
    }

    public void toggleSelectedExcludes() {
        TableCellEditor cellEditor =  table.getDefaultEditor(Boolean.class);
        JCheckBox editorComponent =  (JCheckBox) cellEditor.getTableCellEditorComponent
                (table, true, false, 0, 2);
        for(Category category : getSelectedCategory()){
            editorComponent.setSelected(!category.getExclude());
        }
        cellEditor.stopCellEditing();
    }

    public void setEditorGoal(float newGoal) {
        TableCellEditor cellEditor = table.getDefaultEditor(Float.class);
        JTextField editorComponent = (JTextField) cellEditor.getTableCellEditorComponent
                (table, "", false, 0, 1);
        editorComponent.setText(Float.toString(newGoal));
        cellEditor.stopCellEditing();
    }

    public void setEditorName(String newName) {
        TableCellEditor cellEditor = table.getDefaultEditor(String.class);
        JTextField editorComponent = (JTextField) cellEditor.getTableCellEditorComponent
                (table, "", false, 0, 0);
        editorComponent.setText(newName);
        cellEditor.stopCellEditing();
    }
}
