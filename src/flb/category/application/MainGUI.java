package flb.category.application;

import flb.category.application.listeners.*;
import flb.category.*;
import flb.database.*;
import javax.swing.*;
import java.awt.*;

public class MainGUI {
    private final CategoryStorage categoryStorage;
    private final JFrame frame;
    private final JButton addButton;
    private final JButton deleteButton;
    private final JButton clearAmountButton;
    private final JTable table;
    private final JTextField nameFilter;
    private final CategoryTableModel tableModel;
    private final CategoryTable categoryTable;

    public MainGUI(AbstractDatabase database) {
        this.categoryStorage = new CategoryStorage(database);
        frame = new JFrame();
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        clearAmountButton = new JButton("Clear");
        nameFilter = new JTextField();
        tableModel = new CategoryTableModel(categoryStorage.getCategories(""));
        table = new JTable(tableModel);
        categoryTable = new CategoryTable(table, tableModel);
    }

    public void layout(){
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));
        northPanel.add(nameFilter);
        northPanel.add(addButton);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
        southPanel.add(clearAmountButton);
        southPanel.add(deleteButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(BorderLayout.NORTH, northPanel);
        mainPanel.add(BorderLayout.CENTER, categoryTable.getTablePane());
        mainPanel.add(BorderLayout.SOUTH, southPanel);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(mainPanel);
        frame.setLocation(200, 100);
        frame.setMinimumSize(new Dimension(375, 500));
        frame.setPreferredSize(new Dimension(375, 500));
    }

    public void addListeners() {
        addButton.addActionListener(new UserAddsCategoryListener(categoryStorage, categoryTable, nameFilter));
        deleteButton.addActionListener(new UserDeletesCategoryListener(categoryStorage, categoryTable, nameFilter, frame));
        clearAmountButton.addActionListener(new UserClearsGoalListener(categoryStorage, categoryTable, nameFilter));
        nameFilter.getDocument().addDocumentListener(new UserFiltersCategoriesListener(categoryStorage, categoryTable, nameFilter));
        table.addPropertyChangeListener(new UserRenamesSelectionListener(categoryStorage, categoryTable, nameFilter));
        tableModel.addTableModelListener(new UserEditsGoalAmountListener(categoryStorage, categoryTable, nameFilter));
        table.getDefaultEditor(Boolean.class).addCellEditorListener(new UserEditsExcludesListener(categoryStorage, categoryTable, nameFilter));
    }
    public void launch(){
        frame.pack();
        frame.setVisible(true);
    }

}
