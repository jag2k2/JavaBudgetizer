package flb.components.editors.tables.renderers;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.text.DecimalFormat;

public class SimpleDollarRenderer extends DefaultTableCellRenderer {
    private static final DecimalFormat formatter = new DecimalFormat("$#.00");

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component rendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        float amount = (float) value;
        if (Float.isNaN(amount)) {
            setText("");
        }
        else{
            setText(formatter.format(value));
        }
        return rendererComponent;
    }
}
