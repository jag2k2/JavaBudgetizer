import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class CategoryManager {
    AbstractDatabase database;
    CategoryStorage categoryStorage;
    JTextField nameFilterField;
    CategoryTableModel tableModel;
    ArrayList<Category> categoryList;
    JTable categoryDisplayTable;
    JButton addButton;
    JButton deleteButton;
    JButton renameButton;
    JButton editAmountButton;
    JButton clearAmountButton;
    JButton excludeButton;

    public static void main(String[] args) {
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {e.printStackTrace();}
        CategoryManager categoryManager = new CategoryManager();
        categoryManager.buildGui();
    }

    public CategoryManager() {
        database = new TestDatabase();
        database.connect();
        categoryStorage = new CategoryStorage(database);
        categoryList = categoryStorage.getCategories("");
        nameFilterField = new JTextField();
        tableModel = new CategoryTableModel(categoryList);
        categoryDisplayTable = new JTable(tableModel);
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        renameButton = new JButton("Rename");
        editAmountButton = new JButton("Edit");
        clearAmountButton = new JButton("Clear");
        excludeButton = new JButton("Exclude");
    }

    private void buildGui() {
        JFrame frame = new JFrame();
        BorderLayout borderLayout = new BorderLayout();
        JPanel panel = new JPanel(borderLayout);
        Box northBox = new Box(BoxLayout.X_AXIS);
        Box southBox = new Box(BoxLayout.X_AXIS);

        categoryDisplayTable.setFillsViewportHeight(true);
        categoryDisplayTable.getColumnModel().getColumn(0).setPreferredWidth(180);
        categoryDisplayTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //categoryDisplayTable.getTableHeader().setOpaque(false);
        //categoryDisplayTable.getTableHeader().setBackground(Color.black);
        //categoryDisplayTable.getTableHeader().setForeground(Color.black);

        JScrollPane tableScroller = new JScrollPane(categoryDisplayTable);
        tableScroller.setPreferredSize(new Dimension(250, 300));
        tableScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        tableScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        northBox.add(nameFilterField);
        northBox.add(addButton);
        northBox.add(deleteButton);

        southBox.add(renameButton);
        southBox.add(editAmountButton);
        southBox.add(clearAmountButton);
        southBox.add(excludeButton);

        panel.add(BorderLayout.NORTH, northBox);
        panel.add(BorderLayout.CENTER, tableScroller);
        panel.add(BorderLayout.SOUTH, southBox);
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.setLocation(200,100);
        frame.setMinimumSize(new Dimension(200, 300));
        frame.pack();
        frame.setVisible(true);
    }
}
