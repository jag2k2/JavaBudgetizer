package flb.category.application;

import flb.category.application.listeners.*;
import flb.category.*;
import javax.swing.*;
import java.awt.*;

public class MainPanel {
    private final CategoryStorage categoryStorage;
    private final JPanel mainPanel;
    private final JButton addButton;
    private final JButton deleteButton;
    private final JButton renameButton;
    private final JButton editAmountButton;
    private final JButton clearAmountButton;
    private final JButton excludeButton;
    private final JTextField nameFilter;
    private final CategoryTable categoryTable;

    public MainPanel(CategoryStorage categoryStorage) {
        this.categoryStorage = categoryStorage;
        mainPanel = new JPanel(new BorderLayout());
        nameFilter = new JTextField();
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        renameButton = new JButton("Rename");
        editAmountButton = new JButton("Edit");
        clearAmountButton = new JButton("Clear");
        excludeButton = new JButton("Exclude");
        categoryTable = new CategoryTable(categoryStorage.getCategories(""));
    }

    public void layout(){
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));
        northPanel.add(nameFilter);
        northPanel.add(addButton);
        northPanel.add(deleteButton);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
        southPanel.add(renameButton);
        southPanel.add(editAmountButton);
        southPanel.add(clearAmountButton);
        southPanel.add(excludeButton);

        mainPanel.add(BorderLayout.NORTH, northPanel);
        mainPanel.add(BorderLayout.CENTER, categoryTable.getPane());
        mainPanel.add(BorderLayout.SOUTH, southPanel);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    }

    public void addListeners() {
        nameFilter.getDocument().addDocumentListener(new UserFiltersCategoriesListener(categoryStorage, nameFilter, categoryTable));
        addButton.addActionListener(new UserAddsCategoryListener(categoryStorage, nameFilter, categoryTable));
        deleteButton.addActionListener(new UserDeletesCategoryListener(categoryStorage, nameFilter, categoryTable));
        clearAmountButton.addActionListener(new UserClearsGoalListener(categoryStorage, categoryTable, nameFilter));
    }

    public JPanel getPanel(){
        return mainPanel;
    }
}
