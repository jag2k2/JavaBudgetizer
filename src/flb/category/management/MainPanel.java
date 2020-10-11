package flb.category.management;

import javax.swing.*;
import java.awt.*;

public class MainPanel {
    private final JPanel panel;
    private final NorthPanel northPanel;
    private final CategoryTable categoryTable;
    private final SouthPanel southPanel;

    public MainPanel(NorthPanel northPanel, CategoryTable categoryTable, SouthPanel southPanel) {
        this.panel = new JPanel(new BorderLayout());
        this.northPanel = northPanel;
        this.southPanel = southPanel;
        this.categoryTable = categoryTable;
    }

    public void build(){
        categoryTable.build();
        northPanel.build();
        southPanel.build();

        panel.add(BorderLayout.NORTH, northPanel.getPane());
        panel.add(BorderLayout.CENTER, categoryTable.getPane());
        panel.add(BorderLayout.SOUTH, southPanel.getPane());
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    }

    public JPanel getPanel() {
        return panel;
    }


}
