package flb.category.application;

import flb.category.application.listeners.*;
import flb.category.*;
import flb.database.AbstractDatabase;

import javax.swing.*;
import java.awt.*;

public class MainGUI {
    private final JFrame frame;
    private final JButton addButton;
    private final JButton deleteButton;
    private final JButton clearAmountButton;
    private final JTable table;
    private final JScrollPane tableScroller;
    private final JTextField nameFilter;
    private final CategoryTableModel tableModel;
    private final CategoryTableEditor tableEditor;
    private final AbstractDatabase database;

    public MainGUI(AbstractDatabase database) {
        this.database = database;
        this.tableModel = new CategoryTableModel();
        this.table = new JTable(tableModel);
        this.tableScroller = new JScrollPane(table);
        this.nameFilter = new JTextField();
        frame = new JFrame();
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        clearAmountButton = new JButton("Clear");
        CategoryTable categoryTable = new CategoryTable(table, tableModel);
        CategoryStorage categoryStorage = new CategoryStorageImp(database);
        tableEditor = new CategoryTableEditor(categoryStorage, categoryTable);
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

        tableScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        tableScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(BorderLayout.NORTH, northPanel);
        mainPanel.add(BorderLayout.CENTER, tableScroller);
        mainPanel.add(BorderLayout.SOUTH, southPanel);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(mainPanel);
        frame.setLocation(200, 100);
        frame.setMinimumSize(new Dimension(375, 500));
        frame.setPreferredSize(new Dimension(375, 500));
        frame.pack();
    }

    public void addListeners() {
        addButton.addActionListener(new UserAddsCategoryListener(tableEditor, nameFilter));
        deleteButton.addActionListener(new UserDeletesCategoryListener(tableEditor, nameFilter, frame));
        clearAmountButton.addActionListener(new UserClearsGoalListener(tableEditor, nameFilter));
        nameFilter.getDocument().addDocumentListener(new UserFiltersCategoriesListener(tableEditor, nameFilter));
        table.addPropertyChangeListener(new UserRenamesSelectionListener(tableEditor, nameFilter));
        tableModel.addTableModelListener(new UserEditsGoalAmountListener(tableEditor, nameFilter));
        table.getDefaultEditor(Boolean.class).addCellEditorListener(new UserEditsExcludesListener(tableEditor, nameFilter));
    }
    public void launch(){
        database.connect();
        tableEditor.refresh("");
        frame.setVisible(true);
    }
}
