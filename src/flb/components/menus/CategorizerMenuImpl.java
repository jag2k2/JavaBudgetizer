package flb.components.menus;

import flb.listeners.UserCategorizesTransaction;
import flb.components.editor.transaction.TransactionCategorizer;
import flb.tuples.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class CategorizerMenuImpl implements MenuDisplayer, MenuTester {
    private final JPopupMenu popupMenu;
    private final List<Category> categories;
    private final TransactionCategorizer transactionCategorizer;

    public CategorizerMenuImpl(List<Category> categories, TransactionCategorizer transactionCategorizer) {
        this.popupMenu = new JPopupMenu();
        this.categories = categories;
        this.transactionCategorizer = transactionCategorizer;
    }

    public JPopupMenu getPopup() {
        return popupMenu;
    }

    public void show(JTable table, int row, int column) {
        buildMenu(row);
        Rectangle cellBounds = table.getCellRect(row, column, false);
        popupMenu.show(table, cellBounds.x, cellBounds.y);
    }

    protected void buildMenu(int activeRow) {
        popupMenu.removeAll();
        JMenu superCategory = new JMenu("");
        for (Category category : categories){
            String categoryName = category.getName();
            JMenuItem categoryItem = new JMenuItem();
            categoryItem.addActionListener(new UserCategorizesTransaction(transactionCategorizer));
            if(categoryName.contains("::")){
                String[] elements = categoryName.split("::");
                String superCategoryName = elements[0];
                String subCategoryName = elements[1];
                if (!superCategory.getText().equals(superCategoryName)){
                    superCategory = new JMenu(superCategoryName);
                    popupMenu.add(superCategory);
                }
                categoryItem.setText(subCategoryName);
                categoryItem.setActionCommand(activeRow + "," + superCategoryName + "::" + subCategoryName);
                superCategory.add(categoryItem);
            }
            else{
                categoryItem.setText(categoryName);
                categoryItem.setActionCommand(activeRow + "," + categoryName);
                popupMenu.add(categoryItem);
            }
        }
    }

    public List<String> toStringArray() {
        List<String> stringArray = new ArrayList<>();
        for (MenuElement menuElement : popupMenu.getSubElements()){
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
