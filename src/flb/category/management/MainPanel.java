package flb.category.management;

import flb.category.*;
import javax.swing.*;
import java.awt.*;

public class MainPanel {
    private final JPanel panel;
    private final CategoryStorage categoryStorage;


    public MainPanel(CategoryStorage categoryStorage) {
        this.categoryStorage = categoryStorage;
        this.panel = new JPanel(new BorderLayout());
    }

    public void build(){
        CategoryTable categoryTable = new CategoryTable(categoryStorage.getCategories(""));
        categoryTable.build();
        NorthPanel northPanel = new NorthPanel(categoryStorage, categoryTable);
        northPanel.build();
        SouthPanel southPanel = new SouthPanel();
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
