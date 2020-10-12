package flb.category.application;

import flb.category.Category;
import javax.swing.table.*;
import java.util.*;

public class CategoryTableModel extends AbstractTableModel {

    private final String[] columnNames = {"Name", "Default Goal", "Exclude"};
    private ArrayList<Category> tableContents;

    public CategoryTableModel(ArrayList<Category> tableContents) {
        this.tableContents = tableContents;
    }

    public void setContents(ArrayList<Category> tableContents) {this.tableContents = tableContents;}

    public String getRowName(int row) {
        return tableContents.get(row).getName();
    }

    public Boolean getExcludesAt(int row) {
        return tableContents.get(row).getExclude();
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
        }
    }

    @Override
    public Class getColumnClass(int column) {
        return getValueAt(0, column).getClass();
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return true;
        //return col == 2;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

}
