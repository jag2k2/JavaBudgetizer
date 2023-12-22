package com.jag2k2.components.editor.transaction;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;

public class GreenRowTable extends JTable {
    private final TableModel tableModel;
    private final int columnToCheck;

    public GreenRowTable(TableModel tableModel, int columnToCheck){
        super(tableModel);
        this.tableModel = tableModel;
        this.columnToCheck = columnToCheck;
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component rendererComponent = super.prepareRenderer(renderer, row, column);
        float amount = (float) tableModel.getValueAt(row, columnToCheck);
        if(!super.isRowSelected(row)) {
            rendererComponent.setBackground(CellColor.getBackgroundColor(amount));
        }
        return rendererComponent;
    }
}
