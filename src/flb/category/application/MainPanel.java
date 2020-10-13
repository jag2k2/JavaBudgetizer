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
    private final JButton clearAmountButton;
    private final CategoryTable categoryTable;

    public MainPanel(CategoryStorage categoryStorage) {
        this.categoryStorage = categoryStorage;
        mainPanel = new JPanel(new BorderLayout());
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        clearAmountButton = new JButton("Clear");
        categoryTable = new CategoryTable(categoryStorage.getCategories(""));
    }

    public void layout(){
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));
        northPanel.add(categoryTable.getNameFilter());
        northPanel.add(addButton);


        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
        southPanel.add(clearAmountButton);
        southPanel.add(deleteButton);

        mainPanel.add(BorderLayout.NORTH, northPanel);
        mainPanel.add(BorderLayout.CENTER, categoryTable.getTablePane());
        mainPanel.add(BorderLayout.SOUTH, southPanel);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    }

    public void addListeners() {
        addButton.addActionListener(new UserAddsCategoryListener(categoryStorage, categoryTable));
        deleteButton.addActionListener(new UserDeletesCategoryListener(categoryStorage, categoryTable));
        clearAmountButton.addActionListener(new UserClearsGoalListener(categoryStorage, categoryTable));
        categoryTable.addFilterListener(new UserFiltersCategoriesListener(categoryStorage, categoryTable));
        categoryTable.addRenameEditorListener(new UserRenamesSelectionListener(categoryStorage, categoryTable));
        categoryTable.addGoalAmountEditorListener(new UserEditsGoalAmountListener(categoryStorage, categoryTable));
        categoryTable.addExcludeEditorListener(new UserEditsExcludesListener(categoryStorage, categoryTable));
    }

    public JPanel getPanel(){
        return mainPanel;
    }
}
