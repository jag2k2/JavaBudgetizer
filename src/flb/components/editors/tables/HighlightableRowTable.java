package flb.components.editors.tables;

import flb.components.editors.SummarySelector;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;

public class HighlightableRowTable extends JTable {
    private final SummarySelector summarySelector;
    private final TableModel tableModel;

    public HighlightableRowTable(TableModel tableModel, SummarySelector summarySelector) {
        super(tableModel);
        this.tableModel = tableModel;
        this.summarySelector = summarySelector;
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component rendererComponent = super.prepareRenderer(renderer, row, column);
        if(!super.isRowSelected(row)) {
            String rowCategory = (String)super.getValueAt(row,2);
            float amount = (float) tableModel.getValueAt(row,1);
            rendererComponent.setBackground(getBackgroundColor(amount));
            for (String goalName : summarySelector.getSelectedGoalName()) {
                if (rowCategory.equals(goalName)) {
                    rendererComponent.setBackground(new Color(170,227,242));
                } else {
                    rendererComponent.setBackground(getBackgroundColor(amount));
                }
            }
        }
        return rendererComponent;
    }

    protected Color getBackgroundColor(float amount){
        if (amount > 0) {
            return new Color(207, 255, 207);
        }
        else return null;
    }
}
