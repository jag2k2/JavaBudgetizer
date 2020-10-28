package flb.tables.goal;

import javax.swing.*;

public class GoalTableImp {
    private final GoalTableModelImp tableModel;
    private final JTable table;

    public GoalTableImp(JTable table, GoalTableModelImp tableModel) {
        this.table = table;
        this.tableModel = tableModel;
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);
        table.getColumnModel().getColumn(0).setMinWidth(200);
        table.getColumnModel().getColumn(0).setMaxWidth(200);
        table.getColumnModel().getColumn(1).setMinWidth(65);
        table.getColumnModel().getColumn(1).setMaxWidth(65);
        table.getColumnModel().getColumn(2).setMinWidth(65);
        table.getColumnModel().getColumn(2).setMaxWidth(65);
        table.getColumnModel().getColumn(3).setMinWidth(65);
        table.getColumnModel().getColumn(3).setMaxWidth(65);
    }
}
