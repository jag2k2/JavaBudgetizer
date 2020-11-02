package flb.components;

import flb.application.main.listeners.*;
import flb.database.interfaces.*;
import flb.tables.banking.*;
import flb.tables.credit.*;
import flb.tuples.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class CategoryMenuImpl {
    private final JPopupMenu mainMenu;
    private final CategoryStore categoryStore;
    private final BankingEditorImpl bankingEditor;
    private final CreditEditorImpl creditEditor;

    public CategoryMenuImpl(CategoryStore categoryStore, BankingEditorImpl bankingEditor, CreditEditorImpl creditEditor) {
        this.mainMenu = new JPopupMenu();
        this.categoryStore = categoryStore;
        this.bankingEditor = bankingEditor;
        this.creditEditor = creditEditor;
    }

    public JPopupMenu getPopup() {
        return mainMenu;
    }

    public void show(JTable table, String type, int row, int column) {
        buildMenu(type, row);
        Rectangle cellBounds = table.getCellRect(row, column, false);
        mainMenu.show(table, cellBounds.x, cellBounds.y);
    }

    protected void buildMenu(String activeTable, int activeRow) {
        mainMenu.removeAll();
        JMenu superCategory = new JMenu("");
        for (Category category : categoryStore.getCategories("")){
            String categoryName = category.getName();
            JMenuItem categoryItem = new JMenuItem();
            if (activeTable.equals("banking")){
                categoryItem.addActionListener(new UserCategorizesBankingTransaction(bankingEditor));
            }
            else if (activeTable.equals("credit")){
                categoryItem.addActionListener(new UserCategorizesCreditTransaction(creditEditor));
            }
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
