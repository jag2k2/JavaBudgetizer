package flb.components.editor.transaction.banking;

import flb.components.editor.transaction.TransactionTableModel;
import flb.tuples.*;
import flb.util.*;
import javax.swing.table.*;
import java.util.*;

public class BankingTableModelImp extends AbstractTableModel implements TransactionTableModel {

    private final String[] columnNames = {"Date", "Amount", "Category", "Description"};
    private ArrayList<Transaction> tableContents;

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
        if(column == 0){
            return tableContents.get(row).getDateString();
        } else if (column == 1) {
            return tableContents.get(row).getAmount();
        } else if (column == 2) {
            return tableContents.get(row).getCategoryName();
        } else if (column == 3) {
            return tableContents.get(row).getDescription();
        } else {
            return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Maybe<Transaction> getTransaction(int row) {
        if (row >= 0 && row < getRowCount()) {
            return new Maybe<>(tableContents.get(row));
        }
        else {
            return new Maybe<>();
        }
    }

    @Override
    public ArrayList<Transaction> getTransactions(){
        return tableContents;
    }

    public void updateTransactions(ArrayList<Transaction> tableContents) {
        this.tableContents = tableContents;
        fireTableDataChanged();
    }
}
