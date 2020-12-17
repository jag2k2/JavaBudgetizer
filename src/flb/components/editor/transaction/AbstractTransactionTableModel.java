package flb.components.editor.transaction;

import javax.swing.table.AbstractTableModel;
import flb.tuples.Transaction;
import flb.util.Transactions;
import flb.util.TransactionsImpl;

public abstract class AbstractTransactionTableModel<T extends Transaction> extends AbstractTableModel implements TransactionTableModel<T> {
    private final String[] columnNames;
    private Transactions<T> tableContents = new TransactionsImpl<>();

    public AbstractTransactionTableModel(String... columnNames) {
         this.columnNames = columnNames;
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
        if (column == 0){
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
    public Transactions<T> getTransactions(){
        return tableContents;
    }

    @Override
    public T getTransaction(int row) {
        return tableContents.get(row);
    }

    @Override
    public void updateTransactions(Transactions<T> tableContents) {
        this.tableContents = tableContents;
        fireTableDataChanged();
    }
}
