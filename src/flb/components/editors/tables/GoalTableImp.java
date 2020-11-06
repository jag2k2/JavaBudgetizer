package flb.components.editors.tables;

import flb.components.editors.*;
import flb.components.editors.tables.listeners.UserSelectsGoalListener;
import flb.components.editors.tables.models.GoalTableModelImp;
import flb.components.editors.tables.renderers.*;
import flb.tuples.TransactionSummary;
import flb.util.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.ArrayList;

public class GoalTableImp implements GoalTable, GoalSelector {
    private final GoalTableModelImp tableModel;
    private final JTable table;
    private final JScrollPane scrollPane;
    private final ArrayList<GoalSelectedListener> goalSelectedListeners;

    public GoalTableImp() {
        this.tableModel = new GoalTableModelImp();
        this.table = new JTable(tableModel);
        this.scrollPane = new JScrollPane(table);
        this.goalSelectedListeners = new ArrayList<>();

        layout();
        addListeners();
    }

    protected void layout() {
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);
        SimpleDollarRenderer dollarRenderer = new SimpleDollarRenderer();
        dollarRenderer.setHorizontalAlignment(JLabel.RIGHT);
        table.getColumnModel().getColumn(1).setCellRenderer(dollarRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(dollarRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(dollarRenderer);
        table.getColumnModel().getColumn(0).setMinWidth(185);
        table.getColumnModel().getColumn(0).setMaxWidth(185);
        table.getColumnModel().getColumn(1).setMinWidth(70);
        table.getColumnModel().getColumn(1).setMaxWidth(70);
        table.getColumnModel().getColumn(2).setMinWidth(70);
        table.getColumnModel().getColumn(2).setMaxWidth(70);
        table.getColumnModel().getColumn(3).setMinWidth(70);
        table.getColumnModel().getColumn(3).setMaxWidth(70);
        table.setPreferredScrollableViewportSize(new Dimension(395,-1));

        scrollPane.getViewport().setViewSize(new Dimension(10,10));
        scrollPane.setMaximumSize(new Dimension(10,10));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        Border greyBorder = new LineBorder(Color.LIGHT_GRAY);
        Border margin = BorderFactory.createEmptyBorder(5,5,5,5);
        scrollPane.setBorder(new CompoundBorder(greyBorder, margin));
    }

    protected void addListeners(){
        table.getSelectionModel().addListSelectionListener(new UserSelectsGoalListener(goalSelectedListeners));
    }

    public void addGoalSelectedListener(GoalSelectedListener goalSelectedListener){
        goalSelectedListeners.add(goalSelectedListener);
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
    public Maybe<String> getSelectedGoalName(){
        int selectedRow = table.getSelectedRow();
        return tableModel.getGoalName(selectedRow);
    }
}
