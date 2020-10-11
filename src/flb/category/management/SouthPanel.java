package flb.category.management;

import flb.category.CategoryStorage;

import javax.swing.*;

public class SouthPanel {
    private final JPanel panel;
    private final CategoryStorage categoryStorage;
    private final CategoryTable categoryTable;

    public SouthPanel(CategoryStorage categoryStorage, CategoryTable categoryTable){
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        this.categoryStorage = categoryStorage;
        this.categoryTable = categoryTable;
    }

    public void build() {
        JButton renameButton = new JButton("Rename");
        panel.add(renameButton);

        JButton editAmountButton = new JButton("Edit");
        panel.add(editAmountButton);

        JButton clearAmountButton = new JButton("Clear");
        panel.add(clearAmountButton);
        clearAmountButton.addActionListener(new UserClearsGoalListener(categoryStorage, categoryTable));

        JButton excludeButton = new JButton("Exclude");
        panel.add(excludeButton);
    }

    public JPanel getPane() {
        return panel;
    }
}
