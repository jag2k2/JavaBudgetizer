package flb.application.category;

import flb.application.category.listeners.*;
import flb.database.*;
import flb.tables.category.*;
import javax.swing.*;
import java.awt.*;

public class MainGUI {
    private final JFrame frame;
    private final JButton addButton;
    private final JButton deleteButton;
    private final JButton clearAmountButton;
    private final JTextField nameFilter;
    private final CategoryEditorImp tableEditor;
    private final CategoryTableImp categoryTable;

    public MainGUI(CategoryStore categoryStore) {
        this.nameFilter = new JTextField();
        frame = new JFrame();
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        clearAmountButton = new JButton("Clear");
        categoryTable = new CategoryTableImp();
        tableEditor = new CategoryEditorImp(categoryStore, categoryTable);
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
        mainPanel.add(BorderLayout.CENTER, categoryTable);
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
        categoryTable.addCategoryRenameListener(new UserRenamesSelectionListener(tableEditor, nameFilter));
        categoryTable.addGoalEditListener(new UserEditsGoalAmountListener(tableEditor, nameFilter));
        categoryTable.addExcludesEditListener(new UserEditsExcludesListener(tableEditor, nameFilter));
    }
    public void launch(){
        tableEditor.refreshAndClearSelection("");
        frame.setVisible(true);
    }
}
