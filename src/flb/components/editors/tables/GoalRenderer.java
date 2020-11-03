package flb.components.editors.tables;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.text.DecimalFormat;

public class GoalRenderer extends DefaultTableCellRenderer {
    private static final DecimalFormat formatter = new DecimalFormat("#.00");

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value.equals(Float.NaN)) {
            value = "";
        }
        else{
            value = formatter.format(value);
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}
