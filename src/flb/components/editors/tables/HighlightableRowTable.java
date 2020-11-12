package flb.components.editors.tables;

import flb.components.editors.SummarySelector;

import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;

public class HighlightableRowTable extends GreenRowTable {
    private final SummarySelector summarySelector;
    private final TableModel tableModel;

    public HighlightableRowTable(TableModel tableModel, int columnToCheck, SummarySelector summarySelector) {
        super(tableModel, columnToCheck);
        this.tableModel = tableModel;
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
                    rendererComponent.setBackground(getBackgroundColor());
                }
            }
        }
        return rendererComponent;
    }


}
