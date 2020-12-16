package flb.components.editor.transaction;

import flb.components.editor.summary.SummarySelector;
import javax.swing.table.*;
import java.awt.*;

public class HighlightingRowTable extends GreenRowTable {
    private final SummarySelector summarySelector;

    public HighlightingRowTable(AbstractTableModel tableModel, int columnToCheck, SummarySelector summarySelector) {
        super(tableModel, columnToCheck);
        this.summarySelector = summarySelector;
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component rendererComponent = super.prepareRenderer(renderer, row, column);
        if(!super.isRowSelected(row)) {
            String rowCategory = (String) super.getValueAt(row,2);
            for (String goalName : summarySelector.getSelectedGoalName()) {
                if (rowCategory.equals(goalName)) {
                    rendererComponent.setBackground(new Color(170,227,242));
                }
            }
        }
        return rendererComponent;
    }
}
