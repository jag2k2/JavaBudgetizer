package flb.category.management;

import flb.category.CategoryStorage;
import javax.swing.*;
import java.awt.*;

public class MainGUI {
    private final CategoryStorage categoryStorage;

    public MainGUI(CategoryStorage categoryStorage){
        this.categoryStorage = categoryStorage;
    }

    public void build() {
        CategoryTable categoryTable = new CategoryTable(categoryStorage.getCategories(""));
        NorthPanel northPanel = new NorthPanel(categoryStorage, categoryTable);
        SouthPanel southPanel = new SouthPanel(categoryStorage, categoryTable);
        MainPanel mainPanel = new MainPanel(northPanel, categoryTable, southPanel);
        mainPanel.build();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(mainPanel.getPanel());
        frame.setLocation(200,100);
        frame.setMinimumSize(new Dimension(200, 300));
        frame.pack();
        frame.setVisible(true);
    }
}
