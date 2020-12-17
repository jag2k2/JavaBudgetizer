package flb.components.editor.transaction.credit;

import flb.components.editor.transaction.TransactionTableModel;
import flb.tuples.*;
import flb.util.*;
import javax.swing.table.*;

public class CreditTableModelImpl extends AbstractTableModel implements TransactionTableModel<CreditTransaction> {
    private final String[] columnNames = {"Date", "Amount", "Category", "Description", "Group"};
    private Transactions<CreditTransaction> tableContents;

    public CreditTableModelImpl() {
        tableContents = new TransactionsImpl<>();
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
        if (column == 0) {
            return tableContents.get(row).getDateString();
        } else if (column == 1) {
            return tableContents.get(row).getAmount();
        } else if (column == 2) {
            return tableContents.get(row).getCategoryName();
        } else if (column == 3) {
            return tableContents.get(row).getDescription();
        } else if (column == 4) {
            return tableContents.get(row).getPayGroup();
        } else {
            return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Transactions<CreditTransaction> getTransactions(){
        return tableContents;
    }

    @Override
    public Maybe<CreditTransaction> getTransaction(int row){
        if (row >= 0 && row < getRowCount()) {
            return new Maybe<>(tableContents.get(row));
        }
        else {
            return new Maybe<>();
        }
    }

    public void updateTransactions(Transactions<CreditTransaction> tableContents) {
        this.tableContents = tableContents;
        fireTableDataChanged();
    }
}
