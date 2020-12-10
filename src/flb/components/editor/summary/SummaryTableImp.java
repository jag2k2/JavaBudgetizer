package flb.components.editor.summary;

import flb.components.editor.SimpleDollarRenderer;
import flb.components.editor.transaction.GreenRowTable;
import flb.components.menus.MenuDisplayer;
import flb.listeners.UserRightClicksTableListener;
import flb.tuples.TransactionSummary;
import flb.util.*;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SummaryTableImp extends JComponent implements SummaryTable, SummaryTableTester {
    private final SummaryTableModelImp tableModel;
    private final JTable table;


    public SummaryTableImp() {
        this.tableModel = new SummaryTableModelImp();
        this.table = new GreenRowTable(tableModel, 3);
        JScrollPane scrollPane = new JScrollPane(table);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        TableCellRenderer cellRenderer = new SimpleDollarRenderer(table.getDefaultRenderer(Float.class));
        table.getColumnModel().getColumn(1).setCellRenderer(cellRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(cellRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(cellRenderer);
        table.getColumnModel().getColumn(0).setMinWidth(150);
        table.getColumnModel().getColumn(0).setMaxWidth(150);
        table.getColumnModel().getColumn(1).setMinWidth(70);
        table.getColumnModel().getColumn(1).setMaxWidth(70);
        table.getColumnModel().getColumn(2).setMinWidth(70);
        table.getColumnModel().getColumn(2).setMaxWidth(70);
        table.getColumnModel().getColumn(3).setMinWidth(70);
        table.getColumnModel().getColumn(3).setMaxWidth(70);
        table.setPreferredScrollableViewportSize(new Dimension(360,-1));

        scrollPane.getViewport().setViewSize(new Dimension(10,10));
        scrollPane.setMaximumSize(new Dimension(10,10));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        this.setLayout(new BorderLayout());
        this.add(scrollPane);
    }

    @Override
    public void addGoalSelectionListener(ListSelectionListener listSelectionListener){
        table.getSelectionModel().addListSelectionListener(listSelectionListener);
    }

    @Override
    public void addGoalEditedListener(TableModelListener tableModelListener) {
        tableModel.addTableModelListener(tableModelListener);
    }

    @Override
    public void display(ArrayList<TransactionSummary> tableContents) {
        tableModel.updateSummaries(tableContents);
    }

    @Override
    public void displayAndKeepSelection(ArrayList<TransactionSummary> tableContents) {
        int selectedRow = table.getSelectedRow();
        tableModel.updateSummaries(tableContents);
        table.getSelectionModel().setSelectionInterval(selectedRow, selectedRow);
    }

    @Override
    public Maybe<TransactionSummary> getSelectedSummary(){
        int selectedRow = table.getSelectedRow();
        return tableModel.getSummary(selectedRow);
    }

    @Override
    public Maybe<TransactionSummary> getSummary(int row) {
        return tableModel.getSummary(row);
    }

    public Maybe<String> getSelectedGoalName(){
        int selectedRow = table.getSelectedRow();
        return tableModel.getGoalName(selectedRow);
    }

    @Override
    public void setSelectedRow(int row) {
        table.getSelectionModel().setSelectionInterval(row, row);
    }

    @Override
    public void editCellAt(int row, int col) {
        table.editCellAt(row, col);
    }

    @Override
    public void setEditorGoal(float goalAmount) {
        TableCellEditor cellEditor = table.getDefaultEditor(Float.class);
        JTextField editorComponent = (JTextField) cellEditor.getTableCellEditorComponent(table, "", false, 0, 1);
        editorComponent.setText(Float.toString(goalAmount));
        cellEditor.stopCellEditing();
    }

    @Override
    public void addEditorMenu(MenuDisplayer menuDisplayer) {
        table.add(menuDisplayer.getPopup());
        List<Integer> validGroupingColumns = new ArrayList<>(Collections.singletonList(1));
        table.addMouseListener(new UserRightClicksTableListener(menuDisplayer, validGroupingColumns));
    }
}
