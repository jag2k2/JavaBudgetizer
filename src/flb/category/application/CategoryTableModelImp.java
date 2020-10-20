package flb.category.application;

import flb.category.Category;
import flb.util.*;
import javax.swing.table.*;
import java.util.*;

public class CategoryTableModelImp extends AbstractTableModel implements CategoryTableModel {

    private final String[] columnNames = {"Name", "Default Goal", "Exclude"};
    private ArrayList<Category> tableContents;

    public CategoryTableModelImp() {
        this.tableContents = new ArrayList<>();
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
            case 0 -> tableContents.get(row).getName();
            case 1 -> tableContents.get(row).getDefaultGoal();
            case 2 -> tableContents.get(row).getExclude();
            default -> null;
        };
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            tableContents.get(rowIndex).rename((String) aValue);
        } else if (columnIndex == 1) {
            tableContents.get(rowIndex).setDefaultGoal((Float) aValue);
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public Class getColumnClass(int column) {
        return getValueAt(0, column).getClass();
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return true;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void updateCategories(ArrayList<Category> tableContents) {
        this.tableContents = tableContents;
        fireTableDataChanged();
    }

    public ArrayList<Category> getContents() {
        return tableContents;
    }

    public Maybe<Category> getCategory(int row) {
        if (row >= 0 && row < tableContents.size()) {
            return new Maybe<>(tableContents.get(row));
        }
        else {
            return new Maybe<>();
        }
    }
}
