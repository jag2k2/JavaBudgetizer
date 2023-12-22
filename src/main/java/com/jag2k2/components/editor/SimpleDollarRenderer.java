package com.jag2k2.components.editor;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.text.DecimalFormat;

public class SimpleDollarRenderer implements TableCellRenderer {
    private final TableCellRenderer cellRenderer;
    private static final DecimalFormat formatter = new DecimalFormat("$#.00");

    public SimpleDollarRenderer(TableCellRenderer cellRenderer){
        this.cellRenderer = cellRenderer;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component rendererComponent = cellRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (rendererComponent instanceof JLabel && value instanceof Float) {
            JLabel floatRenderer = (JLabel) rendererComponent;
            float amount = (float) value;

            floatRenderer.setHorizontalAlignment(JLabel.RIGHT);

            if (Float.isNaN(amount)) {
                floatRenderer.setText("");
            } else {
                floatRenderer.setText(formatter.format(value));
            }
        }
        return rendererComponent;
    }
}
