package flb.category.management;

import flb.category.CategoryStorage;
import javax.swing.*;


public class NorthPanel {
    private final CategoryStorage categoryStorage;
    private final CategoryTable categoryTable;
    private final JPanel panel;

    public NorthPanel(CategoryStorage categoryStorage, CategoryTable categoryTable) {
        this.categoryStorage = categoryStorage;
        this.categoryTable = categoryTable;
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
    }

    public void build() {
        JTextField nameFilterField = new JTextField();
        nameFilterField.getDocument().addDocumentListener(new UserFiltersCategoriesListener(categoryStorage, nameFilterField, categoryTable));
        panel.add(nameFilterField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new UserAddsCategoryListener(categoryStorage, nameFilterField, categoryTable));
        panel.add(addButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new UserDeletesCategoryListener(categoryStorage, nameFilterField, categoryTable));
        panel.add(deleteButton);
    }

    public JPanel getPane() {
        return panel;
    }
}
