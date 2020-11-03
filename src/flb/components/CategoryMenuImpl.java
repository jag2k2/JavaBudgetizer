package flb.components;

import flb.application.main.listeners.*;
import flb.database.interfaces.*;
import flb.tables.interfaces.TransactionCategorizer;
import flb.tuples.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class CategoryMenuImpl {
    private final JPopupMenu mainMenu;
    private final CategoryStore categoryStore;
    private final TransactionCategorizer transactionCategorizer;

    public CategoryMenuImpl(CategoryStore categoryStore, TransactionCategorizer transactionCategorizer) {
        this.mainMenu = new JPopupMenu();
        this.categoryStore = categoryStore;
        this.transactionCategorizer = transactionCategorizer;
    }

    public JPopupMenu getPopup() {
        return mainMenu;
    }

    public void show(JTable table, int row, int column) {
        buildMenu(row);
        Rectangle cellBounds = table.getCellRect(row, column, false);
        mainMenu.show(table, cellBounds.x, cellBounds.y);
    }

    protected void buildMenu(int activeRow) {
        mainMenu.removeAll();
        JMenu superCategory = new JMenu("");
        for (Category category : categoryStore.getCategories("")){
            String categoryName = category.getName();
            JMenuItem categoryItem = new JMenuItem();
            categoryItem.addActionListener(new UserCategorizesTransaction(transactionCategorizer));
            if(categoryName.contains("::")){
                String[] elements = categoryName.split("::");
                String superCategoryName = elements[0];
                String subCategoryName = elements[1];
                if (!superCategory.getText().equals(superCategoryName)){
                    superCategory = new JMenu(superCategoryName);
                    mainMenu.add(superCategory);
                }
                categoryItem.setText(subCategoryName);
                categoryItem.setActionCommand(activeRow + "," + superCategoryName + "::" + subCategoryName);
                superCategory.add(categoryItem);
            }
            else{
                categoryItem.setText(categoryName);
                categoryItem.setActionCommand(activeRow + "," + categoryName);
                mainMenu.add(categoryItem);
            }
        }
    }

    protected ArrayList<String> toStringArray() {
        ArrayList<String> stringArray = new ArrayList<>();
        for (MenuElement menuElement : mainMenu.getSubElements()){
            if (menuElement instanceof JMenu){
                JMenu superMenu = (JMenu) menuElement;
                for(MenuElement subMenuElement : superMenu.getPopupMenu().getSubElements()){
                    JMenuItem subMenuItem = (JMenuItem) subMenuElement;
                    stringArray.add(superMenu.getText() + "::" + subMenuItem.getText());
                }
            }
            else if (menuElement instanceof JMenuItem) {
                JMenuItem menuItem = (JMenuItem) menuElement;
                stringArray.add(menuItem.getText());
            }
        }
        return stringArray;
    }
}
