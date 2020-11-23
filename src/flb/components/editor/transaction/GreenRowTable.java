package flb.components.editor.transaction;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;

public class GreenRowTable extends JTable {
    private final TableModel tableModel;
    private final int columnToCheck;
    private float amount = 0F;

    public GreenRowTable(TableModel tableModel, int columnToCheck){
        super(tableModel);
        this.tableModel = tableModel;
        this.columnToCheck = columnToCheck;
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component rendererComponent = super.prepareRenderer(renderer, row, column);
        this.amount = (float) tableModel.getValueAt(row, columnToCheck);
        if(!super.isRowSelected(row)) {
            rendererComponent.setBackground(getBackgroundColor());
        }
        return rendererComponent;
    }

    protected Color getBackgroundColor(){
        if (this.amount > 0) {
            return new Color(207, 255, 207);
        }
        else return null;
    }
}
