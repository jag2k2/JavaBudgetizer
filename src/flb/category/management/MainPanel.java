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

        JTextField nameFilterField = new JTextField();
        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        addButton.addActionListener(new UserAddsCategoryListener(categoryStorage, nameFilterField, categoryTable));
        deleteButton.addActionListener(new UserDeletesCategoryListener(categoryStorage, categoryTable));
        Box northBox = new Box(BoxLayout.X_AXIS);
        northBox.add(nameFilterField);
        northBox.add(addButton);
        northBox.add(deleteButton);

        JButton renameButton = new JButton("Rename");
        JButton editAmountButton = new JButton("Edit");
        JButton clearAmountButton = new JButton("Clear");
        JButton excludeButton = new JButton("Exclude");
        Box southBox = new Box(BoxLayout.X_AXIS);
        southBox.add(renameButton);
        southBox.add(editAmountButton);
        southBox.add(clearAmountButton);
        southBox.add(excludeButton);

        panel.add(BorderLayout.NORTH, northBox);
        panel.add(BorderLayout.CENTER, categoryTable.getPane());
        panel.add(BorderLayout.SOUTH, southBox);
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    }

    public JPanel getPanel() {
        return panel;
    }


}
