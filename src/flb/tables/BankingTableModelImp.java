package flb.tables;

import flb.util.*;
import javax.swing.table.*;
import java.util.*;

public class BankingTableModelImp extends AbstractTableModel {

    private final String[] columnNames = {"Date", "Amount", "Description", "Category"};
    private ArrayList<String> tableContents;

    public BankingTableModelImp() {
        tableContents = new ArrayList<>();
        tableContents.add("10/24/2020");
        tableContents.add("10/25/2020");
        tableContents.add("10/26/2020");
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
            case 1 -> "Amount";
            case 2 -> "Description";
            case 3 -> "Category";
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
