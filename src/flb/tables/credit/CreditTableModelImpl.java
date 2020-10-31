package flb.tables.credit;

import flb.tables.interfaces.*;
import flb.tuples.*;
import flb.util.Maybe;
import javax.swing.table.*;
import java.util.*;

public class CreditTableModelImpl extends AbstractTableModel implements TransactionTableModel {
    private final String[] columnNames = {"Date", "Amount", "Category", "Description", "Group"};
    private ArrayList<CreditTransaction> tableContents;

    public CreditTableModelImpl() {
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
            case 4 -> "";
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

    public void updateTransactions(ArrayList<CreditTransaction> tableContents) {
        this.tableContents = tableContents;
        fireTableDataChanged();
    }

    public ArrayList<CreditTransaction> getTransactions(){
        return tableContents;
    }

    @Override
    public Maybe<Transaction> getTransaction(int row){
        if (row >= 0 && row < tableContents.size()) {
            return new Maybe<>(tableContents.get(row));
        }
        else {
            return new Maybe<>();
        }
    }
}
