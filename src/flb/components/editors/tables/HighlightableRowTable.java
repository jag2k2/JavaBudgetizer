package flb.components.editors.tables;

import flb.components.editors.GoalSelector;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;

public class HighlightableRowTable extends JTable {
    private final GoalSelector goalSelector;

    public HighlightableRowTable(TableModel tableModel, GoalSelector goalSelector) {
        super(tableModel);
        this.goalSelector = goalSelector;
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component rendererComponent = super.prepareRenderer(renderer, row, column);
        if(!super.isRowSelected(row)) {
            String rowCategory = (String)super.getValueAt(row,2);
            for (String goalName : goalSelector.getSelectedGoalName()) {
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
