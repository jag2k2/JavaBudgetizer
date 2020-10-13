package flb.category.application;

import javax.swing.*;
import java.awt.*;

public class MainFrame {
    private final JFrame frame;

    public MainFrame() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void launch(MainPanel mainPanel){
        frame.setContentPane(mainPanel.getPanel());
        frame.setLocation(200, 100);
        frame.setMinimumSize(new Dimension(375, 500));
        frame.setPreferredSize(new Dimension(375, 500));
        frame.pack();
        frame.setVisible(true);
    }
}
