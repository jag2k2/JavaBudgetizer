package flb.components.editors.tables;

import flb.components.editors.SummarySelector;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;

public class HighlightableRowTable extends JTable {
    private final SummarySelector summarySelector;

    public HighlightableRowTable(TableModel tableModel, SummarySelector summarySelector) {
        super(tableModel);
        this.summarySelector = summarySelector;
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component rendererComponent = super.prepareRenderer(renderer, row, column);
        if(!super.isRowSelected(row)) {
            String rowCategory = (String)super.getValueAt(row,2);
            for (String goalName : summarySelector.getSelectedGoalName()) {
                if (rowCategory.equals(goalName)) {
                    rendererComponent.setBackground(new Color(170,227,242));
                } else {
                    rendererComponent.setBackground(null);
                }
            }
        }
        return rendererComponent;
    }
}
