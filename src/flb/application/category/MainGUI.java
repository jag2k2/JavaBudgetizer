package flb.application.category;

import flb.application.category.listeners.*;
import flb.datastores.*;
import flb.components.editors.*;
import javax.swing.*;
import java.awt.*;

public class MainGUI {
    private final JFrame frame;
    private final JButton addButton;
    private final JButton deleteButton;
    private final JButton clearAmountButton;
    private final JTextField nameFilter;
    private final CategoryEditorImpl categoryEditor;

    public MainGUI(CategoryStore categoryStore) {
        this.frame = new JFrame();
        categoryEditor = new CategoryEditorImpl(categoryStore);
        this.nameFilter = new JTextField();
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        clearAmountButton = new JButton("Clear");

        addListeners();
        layout();
    }

    protected void layout(){
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
        mainPanel.add(BorderLayout.CENTER, categoryEditor.getPane());
        mainPanel.add(BorderLayout.SOUTH, southPanel);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(mainPanel);
        frame.setLocation(200, 100);
        frame.setMinimumSize(new Dimension(375, 500));
        frame.setPreferredSize(new Dimension(375, 500));
        frame.pack();
    }

    protected void addListeners() {
        categoryEditor.addCategoryEditingListeners(nameFilter);
        addButton.addActionListener(new UserAddsCategoryListener(categoryEditor, nameFilter));
        deleteButton.addActionListener(new UserDeletesCategoryListener(categoryEditor, nameFilter, frame));
        clearAmountButton.addActionListener(new UserClearsGoalListener(categoryEditor, nameFilter));
        nameFilter.getDocument().addDocumentListener(new UserFiltersCategoriesListener(categoryEditor, nameFilter));
    }

    public void launch(){
        categoryEditor.refreshAndClearSelection("");
        frame.setVisible(true);
    }
}
