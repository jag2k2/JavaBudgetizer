package flb.components.editors.tables.models;

import flb.tuples.*;
import flb.util.Maybe;
import javax.swing.table.*;
import java.util.*;

public class CreditTableModelImpl extends AbstractTableModel implements TransactionTableModel {
    private final String[] columnNames = {"Date", "Amount", "Category", "Description", "Group"};
    private ArrayList<Transaction> tableContents;

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
            case 2 -> tableContents.get(row).getCategoryName();
            case 3 -> tableContents.get(row).getDescription();
            case 4 -> "";
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void updateTransactions(ArrayList<Transaction> tableContents) {
        this.tableContents = tableContents;
        fireTableDataChanged();
    }

    @Override
    public ArrayList<Transaction> getTransactions(){
        return tableContents;
    }

    @Override
    public Maybe<Transaction> getTransaction(int row){
        if (row >= 0 && row < getRowCount()) {
            return new Maybe<>(tableContents.get(row));
        }
        else {
            return new Maybe<>();
        }
    }
}
