import javax.swing.table.*;
import java.util.*;

public class CategoryManagerTableModel extends AbstractTableModel {

    private final String[] columnNames = {"Name", "Default Goal"};
    private final ArrayList<Category> tableContents;

    public CategoryManagerTableModel(ArrayList<Category> tableContents) {
        this.tableContents = tableContents;
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
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
