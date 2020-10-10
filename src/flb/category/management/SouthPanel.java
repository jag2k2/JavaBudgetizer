package flb.category.management;

import javax.swing.*;

public class SouthPanel {
    private final JPanel panel;

    public SouthPanel(){
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
    }

    public void build() {
        JButton renameButton = new JButton("Rename");
        panel.add(renameButton);

        JButton editAmountButton = new JButton("Edit");
        panel.add(editAmountButton);

        JButton clearAmountButton = new JButton("Clear");
        panel.add(clearAmountButton);

        JButton excludeButton = new JButton("Exclude");
        panel.add(excludeButton);
    }

    public JPanel getPane() {
        return panel;
    }
}
