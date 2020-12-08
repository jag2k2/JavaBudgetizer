package flb.components.editor.transaction;

import flb.components.editor.summary.SummarySelector;

import javax.swing.table.*;
import java.awt.*;

public class HighlightingRowTable extends GreenRowTable implements TableHighlighter {
    private final SummarySelector summarySelector;
    private final AbstractTableModel tableModel;

    public HighlightingRowTable(AbstractTableModel tableModel, int columnToCheck, SummarySelector summarySelector) {
        super(tableModel, columnToCheck);
        this.summarySelector = summarySelector;
        this.tableModel = tableModel;
        summarySelector.addGoalSelectedObserver(this);
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component rendererComponent = super.prepareRenderer(renderer, row, column);
        if(!super.isRowSelected(row)) {
            String rowCategory = (String) tableModel.getValueAt(row,2);
            for (String goalName : summarySelector.getSelectedGoalName()) {
                if (rowCategory.equals(goalName)) {
                    rendererComponent.setBackground(new Color(170,227,242));
                } else {
                    rendererComponent.setBackground(getBackgroundColor());
                }
            }
        }
        return rendererComponent;
    }

    @Override
    public void highlightRows() {
        tableModel.fireTableDataChanged();
    }
}
