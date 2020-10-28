package flb.tables.banking;

import flb.tuples.BankingTransaction;

import javax.swing.table.*;
import java.util.*;

public class BankingTableModelImp extends AbstractTableModel {

    private final String[] columnNames = {"Date", "Amount", "Category", "Description"};
    private ArrayList<BankingTransaction> tableContents;

    public BankingTableModelImp() {
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
        return switch (column) {
            case 0 -> tableContents.get(row).getDateString();
            case 1 -> tableContents.get(row).getAmount();
            case 2 -> tableContents.get(row).getCategory();
            case 3 -> tableContents.get(row).getDescription();
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

    public void updateTransactions(ArrayList<BankingTransaction> tableContents) {
        this.tableContents = tableContents;
        fireTableDataChanged();
    }

    public ArrayList<BankingTransaction> getContents() {
        return tableContents;
    }
}
