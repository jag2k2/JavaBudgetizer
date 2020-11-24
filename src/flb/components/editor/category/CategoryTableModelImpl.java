package flb.components.editor.category;

import flb.tuples.Category;
import flb.util.*;
import javax.swing.table.*;
import java.util.*;

public class CategoryTableModelImpl extends AbstractTableModel implements CategoryTableModel {

    private final String[] columnNames = {"Name", "Default Goal", "Exclude"};
    private ArrayList<Category> tableContents;

    public CategoryTableModelImpl() {
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
        if (column == 0) {
            return tableContents.get(row).getName();
        } else if (column == 1) {
            return tableContents.get(row).getDefaultGoalDisplay();
        } else if (column == 2) {
            return tableContents.get(row).getExclude();
        } else {
            return null;
        }
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

    @Override
    public ArrayList<Category> getCategories() {
        return tableContents;
    }

    @Override
    public void updateCategories(ArrayList<Category> tableContents) {
        this.tableContents = tableContents;
        fireTableDataChanged();
    }

    @Override
    public Maybe<Category> getCategory(int row) {
        if (row >= 0 && row < getRowCount()) {
            return new Maybe<>(tableContents.get(row));
        }
        else {
            return new Maybe<>();
        }
    }
}
