package flb.components.editors.tables;

import flb.components.editors.tables.models.GoalTableModelImp;
import flb.tuples.TransactionSummary;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.ArrayList;

public class GoalTableImp implements GoalTable {
    private final GoalTableModelImp tableModel;
    private final JTable table;
    private final JScrollPane scrollPane;

    public GoalTableImp() {
        this.tableModel = new GoalTableModelImp();
        this.table = new JTable(tableModel);
        this.scrollPane = new JScrollPane(table);

        layout();
    }

    protected void layout() {
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);
        DollarAmountRenderer dollarAmountRenderer = new DollarAmountRenderer();
        dollarAmountRenderer.setHorizontalAlignment(JLabel.RIGHT);
        table.getColumnModel().getColumn(1).setCellRenderer(dollarAmountRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(dollarAmountRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(dollarAmountRenderer);
        table.getColumnModel().getColumn(0).setMinWidth(200);
        table.getColumnModel().getColumn(0).setMaxWidth(200);
        table.getColumnModel().getColumn(1).setMinWidth(65);
        table.getColumnModel().getColumn(1).setMaxWidth(65);
        table.getColumnModel().getColumn(2).setMinWidth(65);
        table.getColumnModel().getColumn(2).setMaxWidth(65);
        table.getColumnModel().getColumn(3).setMinWidth(65);
        table.getColumnModel().getColumn(3).setMaxWidth(65);
        table.setPreferredScrollableViewportSize(new Dimension(395,-1));

        scrollPane.getViewport().setViewSize(new Dimension(10,10));
        scrollPane.setMaximumSize(new Dimension(10,10));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        Border greyBorder = new LineBorder(Color.LIGHT_GRAY);
        Border margin = BorderFactory.createEmptyBorder(5,5,5,5);
        scrollPane.setBorder(new CompoundBorder(greyBorder, margin));
    }

    @Override
    public JScrollPane getPane(){
        return scrollPane;
    }

    @Override
    public void display(ArrayList<TransactionSummary> tableContents) {
        tableModel.updateSummaries(tableContents);
    }
}
