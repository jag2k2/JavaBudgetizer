package flb.category.management;

import flb.category.CategoryStorage;

import javax.swing.*;
import java.awt.*;

public class MainGUI {

    public void buildGUI(CategoryStorage categoryStorage) {
        MainPanel mainPanel = new MainPanel(categoryStorage);
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
