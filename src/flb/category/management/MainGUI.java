package flb.category.management;

import javax.swing.*;
import java.awt.*;

public class MainGUI {

    public void buildGUI(MainPanel mainPanel) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(mainPanel.getPanel());
        frame.setLocation(200,100);
        frame.setMinimumSize(new Dimension(200, 300));
        frame.pack();
        frame.setVisible(true);
    }
}
