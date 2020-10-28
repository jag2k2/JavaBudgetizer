package flb.application.category;

import flb.application.category.listeners.*;
import flb.database.*;
import flb.tables.category.CategoryTable;
import flb.tables.category.CategoryTableEditorImp;
import flb.tables.category.CategoryTableImp;
import flb.tables.category.CategoryTableModelImp;

import javax.swing.*;
import java.awt.*;

public class MainGUI {
    private final JFrame frame;
    private final JButton addButton;
    private final JButton deleteButton;
    private final JButton clearAmountButton;
    private final JTable table;
    private final JTextField nameFilter;
    private final CategoryTableModelImp tableModel;
    private final CategoryTableEditorImp tableEditor;
    private final AbstractDatabase database;

    public MainGUI(AbstractDatabase database) {
        this.database = database;
        this.tableModel = new CategoryTableModelImp();
        this.table = new JTable(tableModel);
        this.nameFilter = new JTextField();
        frame = new JFrame();
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        clearAmountButton = new JButton("Clear");
        CategoryTable categoryTable = new CategoryTableImp(table, tableModel);
        CategoryStoreEditor categoryStoreEditor = new CategoryStoreEditorImp(database);
        tableEditor = new CategoryTableEditorImp(categoryStoreEditor, categoryTable);
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

        JScrollPane tableScroller = new JScrollPane(table);
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
        tableEditor.refreshAndClearSelection("");
        frame.setVisible(true);
    }
}
