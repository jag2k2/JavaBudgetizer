package com.jag2k2.components.editor.summary;

import com.jag2k2.tuples.*;
import javax.swing.table.*;
import java.util.*;
import com.jag2k2.util.*;

public class SummaryTableModelImp extends AbstractTableModel implements SummaryTableModel {
    private final String[] columnNames = {"Categories", "Goal", "Actual", "Diff"};
    private ArrayList<TransactionSummary> tableContents;

    public SummaryTableModelImp() {
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
    public Class getColumnClass(int column) {
        return getValueAt(0, column).getClass();
    }

    @Override
    public Object getValueAt(int row, int column) {
        TransactionSummary transactionSummary = tableContents.get(row);
        if (column == 0) {
            return transactionSummary.getName();
        } else if (column == 1) {
            return transactionSummary.getGoalAmountWithDefault(Float.NaN);
        } else if (column == 2) {
            return transactionSummary.getSumWithDefault(Float.NaN);
        } else if (column == 3) {
            return transactionSummary.getCategoryBalance();
        } else {
            return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 1) {
            tableContents.get(rowIndex).addGoal((Float) aValue);
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col == 1;
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

    @Override
    public Maybe<TransactionSummary> getSummary(int row) {
        if (row >= 0 && row < getRowCount()) {
            return new Maybe<>(tableContents.get(row));
        }
        else return new Maybe<>();
    }
}
