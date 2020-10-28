package flb.tables.goal;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class GoalTableModelImp extends AbstractTableModel {
    private final String[] columnNames = {"Categories", "Goal", "Actual", "Diff"};
    private ArrayList<String> tableContents;

    public GoalTableModelImp() {
        tableContents = new ArrayList<>();
        tableContents.add("Category");
        tableContents.add("Another Category");
        tableContents.add("Yet Another Category");
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
        return switch (column) {
            case 0 -> tableContents.get(row);
            case 1 -> "1000.00";
            case 2 -> "2500.00";
            case 3 -> "-1500.00";
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
}
