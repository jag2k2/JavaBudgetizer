import javax.swing.*;
import java.awt.*;
import java.util.*;

public class CategoryManager {
    CategoryStorage categoryStorage;
    JTextField nameFilterField;
    CategoryManagerTableModel tableModel;
    ArrayList<Category> categoryList;
    JTable categoryDisplayTable;
    JButton addCategoryButton;
    JButton deleteCategoryButton;
    JButton renameCategoryButton;
    JButton editAmountButton;
    JButton clearAmountButton;
    JButton excludeCategoryButton;

    public static void main(String[] args) {
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {e.printStackTrace();}
        CategoryManager categoryManager = new CategoryManager();
        categoryManager.buildGui();
    }

    public CategoryManager() {
        categoryStorage = new CategoryStorage("gringotts");
        categoryList = categoryStorage.getCategories("");
        nameFilterField = new JTextField();
        tableModel = new CategoryManagerTableModel(categoryList);
        categoryDisplayTable = new JTable(tableModel);
        addCategoryButton = new JButton("Add");
        deleteCategoryButton = new JButton("Delete");
        renameCategoryButton = new JButton("Rename");
        editAmountButton = new JButton("Edit");
        clearAmountButton = new JButton("Clear");
        excludeCategoryButton = new JButton("Exclude");
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
        northBox.add(addCategoryButton);
        northBox.add(deleteCategoryButton);

        southBox.add(renameCategoryButton);
        southBox.add(editAmountButton);
        southBox.add(clearAmountButton);
        southBox.add(excludeCategoryButton);

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
