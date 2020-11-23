package flb.components.editor.summary;

import flb.components.editor.SimpleDollarRenderer;
import flb.components.editor.transaction.GreenRowTable;
import flb.components.editor.transaction.TableHighlighter;
import flb.components.menus.MenuDisplayer;
import flb.listeners.UserRightCicksMonthGoalListener;
import flb.listeners.UserSelectsGoalListener;
import flb.tuples.TransactionSummary;
import flb.util.*;
import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.ArrayList;

public class SummaryTableImp implements SummaryTable, SummarySelector, SummaryTableTester {
    private final SummaryTableModelImp tableModel;
    private final JTable table;
    private final JScrollPane scrollPane;
    private final ArrayList<TableHighlighter> tableHighlighters;

    public SummaryTableImp() {
        this.tableModel = new SummaryTableModelImp();
        this.table = new GreenRowTable(tableModel, 3);
        this.scrollPane = new JScrollPane(table);
        this.tableHighlighters = new ArrayList<>();

        layout();
        addListeners();
    }

    protected void layout() {
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        SimpleDollarRenderer dollarRenderer = new SimpleDollarRenderer();
        dollarRenderer.setHorizontalAlignment(JLabel.RIGHT);
        table.getColumnModel().getColumn(1).setCellRenderer(dollarRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(dollarRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(dollarRenderer);
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
    }

    protected void addListeners(){
        table.getSelectionModel().addListSelectionListener(new UserSelectsGoalListener(tableHighlighters));
    }

    @Override
    public void addGoalSelectedObserver(TableHighlighter tableHighlighter){
        tableHighlighters.add(tableHighlighter);
    }

    @Override
    public void addGoalEditedListener(TableModelListener tableModelListener) {
        tableModel.addTableModelListener(tableModelListener);
    }

    @Override
    public JScrollPane getPane(){
        return scrollPane;
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

    @Override
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
        table.addMouseListener(new UserRightCicksMonthGoalListener(menuDisplayer));
    }
}
