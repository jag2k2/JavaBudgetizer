package flb.components.editors.tables.models;

import flb.tuples.TransactionSummary;
import javax.swing.table.*;
import java.util.*;
import flb.util.*;

public class GoalTableModelImp extends AbstractTableModel implements GoalTableModel {
    private final String[] columnNames = {"Categories", "Goal", "Actual", "Diff"};
    private ArrayList<TransactionSummary> tableContents;

    public GoalTableModelImp() {
        tableContents = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return tableContents.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int row, int column) {
        TransactionSummary transactionSummary = tableContents.get(row);
        return switch (column) {
            case 0 -> transactionSummary.getName();
            case 1 -> transactionSummary.getGoalAmount();
            case 2 -> transactionSummary.getTransactionSum();
            case 3 -> transactionSummary.getGoalAmount() + transactionSummary.getTransactionSum();
            default -> null;
        };
    }

    /*@Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            tableContents.get(rowIndex).rename((String) aValue);
        } else if (columnIndex == 1) {
            tableContents.get(rowIndex).setDefaultGoal((Float) aValue);
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }*/

    /*@Override
    public boolean isCellEditable(int row, int col) {
        return true;
    }*/

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public Maybe<String> getGoalName(int row){
        if (row >= 0 && row < getRowCount()){
            return new Maybe<>(tableContents.get(row).getName());
        }
        else return new Maybe<>();
    }

    public void updateSummaries(ArrayList<TransactionSummary> tableContents) {
        this.tableContents = tableContents;
        fireTableDataChanged();
    }
}
